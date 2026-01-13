package in.sp.main.service;

import java.util.List;
import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.join.ConcatenationDocumentJoiner;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import in.sp.main.doc_readers.MyJsonAndPdfReaders;
import in.sp.main.doc_transformers.MyTextSplitter;
import in.sp.main.post_processor.DistinctDocumentPostProcessor;
import in.sp.main.tools.SimpleDateTimeTool;
import in.sp.main.tools.WeatherTool;
import reactor.core.publisher.Flux;


@Service
public class ChatServiceImpl implements ChatService{
	int i; // will be initialized with 0 (default value)
	
	private static final PromptTemplate HYBRID_PROMPT_TEMPLATE =
		    new PromptTemplate("""
		    Context information is below.

		    ---------------------
		    {context}
		    ---------------------

		    Answer the query.

		    Rules:
		    - If the context contains relevant information, use it and cite it.
		    - If the context is insufficient or irrelevant, answer using your general knowledge or use the appropriate tools provided to the LLM.
		    - Do NOT say "I don't know" solely because the context is incomplete.
		    - Do NOT invent citations.

		    Query: {query}

		    Answer:
		    """);

	
	@Autowired
	private VectorStore vectorStore;
	
	@Autowired
	private ChatClient chatClient;
	
	@Autowired
	private MyJsonAndPdfReaders readers;
	
	@Autowired
	private MyTextSplitter splitter;
	
	@Autowired
	private SimpleDateTimeTool dateTimeTool;
	
	@Autowired
	private WeatherTool weatherTool;
	
	@Autowired
	private DistinctDocumentPostProcessor postProcessor;


	@Value("classpath:prompts/user-prompt.st")
	private Resource userResource;

	@Value("classpath:prompts/system-prompt.st")
	private Resource systemResource;
	
	@Override
	public String chatTemplate(String query, String userId) {
		var raAdvisor = RetrievalAugmentationAdvisor.builder()
				// pre-retrieval module
				.queryTransformers(
						RewriteQueryTransformer.builder().chatClientBuilder(chatClient.mutate().clone()).build(),
						TranslationQueryTransformer.builder().chatClientBuilder(chatClient.mutate().clone()).targetLanguage("English").build()
						)
				.queryExpander(
						MultiQueryExpander.builder().chatClientBuilder(chatClient.mutate().clone()).build()
						)
				// retrieval module
				.documentRetriever(VectorStoreDocumentRetriever.builder()
						.vectorStore(vectorStore).similarityThreshold(0.5).topK(4).build())
				.documentJoiner(new ConcatenationDocumentJoiner())
				// post-retrieval module (we have to use our custom DocumentPostProcessor)
				.documentPostProcessors(postProcessor)
				// generation module
				.queryAugmenter(ContextualQueryAugmenter.builder()
						.promptTemplate(HYBRID_PROMPT_TEMPLATE)
						.allowEmptyContext(true)
						.emptyContextPromptTemplate(PromptTemplate
								.builder()
								.template(
										"""
						                If no retrieval context is available.
						                Answer the query using your general knowledge.
						                """
										)
								.build())
						.build())		
				.build();
		
		String response = chatClient
				.prompt()
				.advisors(raAdvisor)
				.advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, userId))
				.system(systemResource)
				.user(u -> u.text(userResource).params(Map.of("query", query)))	
				.tools(dateTimeTool, weatherTool)
				.call()
				.content();
		return response;
	}
	
	@Override
	public Flux<String> streamChatTemplate(String query, String userId) {
		var raAdvisor = RetrievalAugmentationAdvisor.builder()
				// pre-retrieval module
				.queryTransformers(
						RewriteQueryTransformer.builder().chatClientBuilder(chatClient.mutate().clone()).build()
						)
				.queryExpander(
						MultiQueryExpander.builder().chatClientBuilder(chatClient.mutate().clone()).build()
						)
				// retrieval module
				.documentRetriever(VectorStoreDocumentRetriever.builder()
						.vectorStore(vectorStore).similarityThreshold(0.5).topK(4).build())
				.documentJoiner(new ConcatenationDocumentJoiner())
				// post-retrieval module (we have to use our custom DocumentPostProcessor)
				.documentPostProcessors(postProcessor)
				// generation module
				.queryAugmenter(ContextualQueryAugmenter.builder()
						.promptTemplate(HYBRID_PROMPT_TEMPLATE)
						.allowEmptyContext(true)
						.emptyContextPromptTemplate(PromptTemplate
								.builder()
								.template(
										"""
						                No retrieval context is available.
						                Answer the query using your general knowledge.
						                """
										)
								.build())
						.build())
				
				.build();
	
		Flux<String> streamResponse = chatClient
		.prompt()
		.advisors(raAdvisor)
		.advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, userId))
		.system(systemResource)
		.user(u -> u.text(userResource).params(Map.of("query", query)))		
		.tools(dateTimeTool, weatherTool)
		.stream()
		.content();
		return streamResponse;
	}



	@Override
	public void addToVectorDb() {
				
		// Step 1: Extract the raw data from the source
		List<Document> jsonAsDocuments = readers.loadJsonAsDocuments();
		List<Document> pdfAsDocuments = readers.loadPdfAsDocuments();
		
		// Step 2: Transform the documents into usable chunks
		List<Document> transformDataJson = splitter.transformData(jsonAsDocuments);
		List<Document> transformDataPdf = splitter.transformData(pdfAsDocuments);

		// Step 3: Load the documents in vector store
		if (i == 0) { // as we want to dump the data only once
		
			vectorStore.add(transformDataJson); // we add the documents to vectorDB (we can do this by exposing an end point
                                                // or by running the method in integration test
			vectorStore.add(transformDataPdf);
		}
		i++;
	}



}

package in.sp.main.post_processor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.postretrieval.document.DocumentPostProcessor;
import org.springframework.stereotype.Component;


@Component
public class DistinctDocumentPostProcessor implements DocumentPostProcessor{
	
	private static final Logger log = LoggerFactory.getLogger(DistinctDocumentPostProcessor.class);


	@Override
	public List<Document> process(Query query, List<Document> documents) {
		
		log.info("Removing the duplicate documents during post-retrieval");
		
		// The Document class has equals() and hashCode() methods. So, we can use distinct() method 
		List<Document> list = documents.stream().distinct().toList(); 
		return list;
	}

}

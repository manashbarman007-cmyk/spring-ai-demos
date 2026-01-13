package in.sp.main.doc_transformers;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Component;

@Component
public class MyTextSplitter implements DataTransformer{

	@Override
	public List<Document> transformData(List<Document> documents) {
		TokenTextSplitter splitter = new TokenTextSplitter();
		return splitter.apply(documents);
	}

}

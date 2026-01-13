package in.sp.main.doc_transformers;

import java.util.List;

import org.springframework.ai.document.Document;

public interface DataTransformer {
	List<Document> transformData(List<Document> documents);
}

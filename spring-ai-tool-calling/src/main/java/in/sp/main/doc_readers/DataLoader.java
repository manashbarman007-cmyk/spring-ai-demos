package in.sp.main.doc_readers;

import java.util.List;

import org.springframework.ai.document.Document;

public interface DataLoader {
	
	List<Document> loadJsonAsDocuments();
	
	List<Document> loadPdfAsDocuments();

}

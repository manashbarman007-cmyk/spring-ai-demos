package in.sp.main.doc_readers;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class MyJsonAndPdfReaders implements DataLoader{
	
	@Value("classpath:sample.json")
	private Resource jsonResource;
	
	@Value("classpath:vanka.pdf")
	private Resource pdfResource;
	
	@Override
	public List<Document> loadJsonAsDocuments() {
		JsonReader jsonReader = new JsonReader(jsonResource, "name", "grade", "hobbies");
		return jsonReader.get(); // returns the JSON data as List of Document object 
	}

	@Override
	public List<Document> loadPdfAsDocuments() {

		PagePdfDocumentReader paragraphPdfDocumentReader = new PagePdfDocumentReader(pdfResource, PdfDocumentReaderConfig.builder()
				.withPageTopMargin(0).withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
						.withNumberOfTopTextLinesToDelete(0).build())
				.withPagesPerDocument(1)
				.build()); 
		
		
		return paragraphPdfDocumentReader.read(); // returns the PDF data as List of Document object 
	}

}

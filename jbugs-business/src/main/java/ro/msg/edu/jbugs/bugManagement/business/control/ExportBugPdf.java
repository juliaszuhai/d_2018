package ro.msg.edu.jbugs.bugManagement.business.control;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugManagement.business.exceptions.ExceptionCode;


import javax.ejb.Stateless;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Stateless
public class ExportBugPdf {

    private static String FILE = "t:/BugPdf.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);

    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);


    public  void createPdf(BugDTO bugDTO) throws BusinessException {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addContent(document,bugDTO);
            document.close();

        } catch (FileNotFoundException e) {
            throw new BusinessException(ExceptionCode.BUG_NOT_EXPORTED);
        } catch (DocumentException e) {
            throw new BusinessException(ExceptionCode.BUG_NOT_EXPORTED);
        }
    }

    private  void addContent(Document document,BugDTO bugDTO) throws BusinessException {
        document.addTitle("Bug");
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Bug "+bugDTO.getTitle(), catFont));
        addEmptyLine(preface, 1);
        DateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        String targetDate = formatterDate.format(bugDTO.getTargetDate());
        preface.add(new Paragraph("Description: "+bugDTO.getDescription(),smallBold));
        preface.add(new Paragraph("Version: "+bugDTO.getVersion(),smallBold));
        preface.add(new Paragraph("Target date: "+targetDate,smallBold));
        preface.add(new Paragraph("Status: "+bugDTO.getStatus(),smallBold));
        preface.add(new Paragraph("Fixed version: "+bugDTO.getFixedVersion(),smallBold));
        preface.add(new Paragraph("Severity: "+bugDTO.getSeverity(),smallBold));
        preface.add(new Paragraph("Created by: "+bugDTO.getCreatedByUser().getUsername(),smallBold));
        preface.add(new Paragraph("Assigned to: "+bugDTO.getAssignedTo().getUsername(),smallBold));
        Font code = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL, BaseColor.RED);
        preface.add(createBgChunk("HelloWorldStyles", code));
        try {
            document.add(preface);
        } catch (DocumentException e) {
            throw new BusinessException(ExceptionCode.BUG_NOT_EXPORTED);
        }


    }
    private Chunk createBgChunk(String s, Font font) {
        Chunk chunk = new Chunk(s, font);
        chunk.setBackground(BaseColor.LIGHT_GRAY);
        return chunk;
    }
    private  void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}



package br.com.unimedcuritiba.core.xml;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.DefaultHandler;

public class ErrorChecker extends DefaultHandler {
	
	private static Logger logger = Logger.getLogger(ErrorChecker.class);
	
	private List<String> erros = new ArrayList<String>();
	private List<String> alertas = new ArrayList<String>();
    
    public void error(org.xml.sax.SAXParseException s) throws org.xml.sax.SAXException {
    	StringBuffer erro = new StringBuffer();
    	erro.append("Documento XML com erros de formata��o. Linha ");
    	erro.append(s.getLineNumber());
    	erro.append(" Detalhes T�cnicos: ");
    	erro.append(ErrorTranslator.translate(s));
    	
    	logger.debug("XML ERRO:" + s.getLocalizedMessage() +" At line "+s.getLineNumber()+" and column "+s.getColumnNumber());
    	erros.add(erro.toString());
    	//s.printStackTrace();
    }

    public void fatalError(org.xml.sax.SAXParseException s) throws org.xml.sax.SAXException {
    	StringBuffer erro = new StringBuffer();
    	erro.append("Documento XML com erros de formata��o. Linha ");
    	erro.append(s.getLineNumber());
    	erro.append(" Detalhes T�cnicos: ");
    	erro.append(ErrorTranslator.translate(s));
    	
    	logger.info(erro.toString());
    	erros.add(erro.toString());
    	//s.printStackTrace();
    }

    public void warning(org.xml.sax.SAXParseException s) throws org.xml.sax.SAXException {
    	StringBuffer erro = new StringBuffer();
    	erro.append("Documento XML com alertas. Linha ");
    	erro.append(s.getLineNumber());
    	erro.append(" Detalhes T�cnicos: ");
    	erro.append(ErrorTranslator.translate(s));
    	
    	logger.info(erro.toString());
    	alertas.add(erro.toString());
    	//s.printStackTrace();
    }

	public List<String> getErros() {
		return erros;
	}
	public List<String> getAlertas() {
		return alertas;
	}
}

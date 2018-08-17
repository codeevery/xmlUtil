/**
 * 
 */
package znet.util;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import znet.entity.MyMap;


public class XmlConvertUtil {
	public static void maptoXml(
			Map<Map<String, String>,List<Map<String, String>>>map, String date,
			String path) {
		Document document = DocumentHelper.createDocument();
		Element nodesElement = document.addElement("root");
		nodesElement.addAttribute("code", "DB14/T 671.1-2012");
		Element headElement = nodesElement.addElement("head");
		headElement.addAttribute("cs_mine_code",
				MyMap.mineinfo.getCollieryEncode());
		headElement.addAttribute("cs_data_time", date);
		for (Map<String, String> mroot : map.keySet()) {
			Element nodeElement = nodesElement.addElement("data");
			for (Object r : mroot.keySet()) {
				nodeElement.addAttribute(String.valueOf(r),
						String.valueOf(mroot.get(r)));
			}
			List<Map<String, String>> me = map.get(mroot);
			for(Map<String,String>lm:me ){
				Element pointElement = nodeElement.addElement("point");
				for (Object e : lm.keySet()) {
						pointElement.addAttribute(String.valueOf(e),
								String.valueOf(lm.get(e)));
					} 
			}
			
		}

		doc2String(document, path);

	}

	public static void listtoXml(List list, String date, String path) {
		Document document = DocumentHelper.createDocument();
		Element nodesElement = document.addElement("root");
		nodesElement.addAttribute("code", "DB14/T 671.1-2012");
		Element headElement = nodesElement.addElement("head");
		headElement.addAttribute("cs_mine_code",
				MyMap.mineinfo.getCollieryEncode());
		headElement.addAttribute("cs_data_time", date);
		for (Object o : list) {
			Element nodeElement = nodesElement.addElement("data");
			if (o instanceof Map) {
				for (Object obj : ((Map) o).keySet()) {
					if (obj != null) {
						nodeElement.addAttribute(String.valueOf(obj),
								String.valueOf(((Map) o).get(obj)));
					}
				}
			}
		}
		doc2String(document, path);
	}

	public static void doc2String(Document document, String path) {
		XMLWriter writer;
		try {
			// 使用输出流来进行转化
			FileOutputStream out = new FileOutputStream(path);
			writer = new XMLWriter(out, OutputFormat.createPrettyPrint());
			writer.setEscapeText(false);
			writer.write(document);
			writer.flush();
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

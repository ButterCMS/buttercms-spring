package com.buttercms.example.controllers;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Node;

import com.buttercms.IButterCMSClient;

@Controller
@RequestMapping("/feed")
public class FeedController {
  private IButterCMSClient butterCMSClient;

  public FeedController(IButterCMSClient butterCMSClient) {
    this.butterCMSClient = butterCMSClient;
  }

  @RequestMapping("/rss")
  public void getRSS(HttpServletResponse response) throws IOException, TransformerException {
    response.setContentType("application/xml");
    ServletOutputStream outStream = response.getOutputStream();
    outStream.println(xmlToString(butterCMSClient.getRSS()));
    outStream.flush();
    outStream.close();
  }

  @RequestMapping("/atom")
  public void getAtom(HttpServletResponse response) throws IOException, TransformerException {
    response.setContentType("application/xml");
    ServletOutputStream outStream = response.getOutputStream();
    outStream.println(xmlToString(butterCMSClient.getAtom()));
    outStream.flush();
    outStream.close();
  }

  @RequestMapping("/sitemap")
  public void getSiteMap(HttpServletResponse response) throws IOException, TransformerException {
    response.setContentType("application/xml");
    ServletOutputStream outStream = response.getOutputStream();
    outStream.println(xmlToString(butterCMSClient.getSiteMap()));
    outStream.flush();
    outStream.close();
  }

  private String xmlToString(Node node) throws TransformerException {

    DOMSource domSource = new DOMSource(node);
    StringWriter writer = new StringWriter();
    StreamResult result = new StreamResult(writer);
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    transformer.transform(domSource, result);
    return writer.toString();
  }
}

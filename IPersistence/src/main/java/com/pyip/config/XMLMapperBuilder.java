package com.pyip.config;

import com.pyip.pojo.Configuration;
import com.pyip.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream resourceAsSteam) throws Exception {
        Document document = new SAXReader().read(resourceAsSteam);
        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");
        List<Element> select = rootElement.selectNodes("//select");
        for (Element element : select) {
            String id = element.attributeValue("id");
            String parameterType = element.attributeValue("parameterType");
            String resultType = element.attributeValue("resultType");

//            Class<?> parameterTypeClass = getClassType(parameterType);
//            Class<?> resultTypeClass = getClassType(resultType);

            String key = namespace+"."+id;
            String textTrim = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setResultType(resultType);
//            mappedStatement.setParameterType(parameterTypeClass);
//            mappedStatement.setResultType(resultTypeClass);
            mappedStatement.setSql(textTrim);

            configuration.getMappedStatementMap().put(key,mappedStatement);
        }

    }



}

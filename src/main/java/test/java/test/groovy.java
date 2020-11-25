package test.java.test;


import groovy.lang.Binding;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.codehaus.groovy.runtime.InvokerHelper;

import javax.sql.DataSource;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.PreparedStatement;


public class groovy {
    public static void main(String[] args) throws IOException, ResourceException, ScriptException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        List<String> list = new ArrayList<String>();
        list.add("https://news.nate.com/view/20201125n40664?modit=1606306262");
        list.add("https://news.nate.com/view/20201125n30456?mid=n0200");

        GroovyScriptEngine engine = new GroovyScriptEngine( "." );

        Object instance = engine
                .loadScriptByName("src/test.groovy")
                .newInstance();

        Object result = InvokerHelper.invokeMethod(instance, "getDetailArticle", list);
        System.out.println(result);
    }

}

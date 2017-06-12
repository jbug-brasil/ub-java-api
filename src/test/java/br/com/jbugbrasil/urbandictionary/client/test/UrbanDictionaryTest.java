/*
 MIT License

 Copyright (c) 2017 JBUG:Brasil

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package br.com.jbugbrasil.urbandictionary.client.test;

import br.com.jbugbrasil.urbandictionary.client.UrbanDictionaryClient;
import br.com.jbugbrasil.urbandictionary.client.builder.UrbanDictionaryClientBuilder;
import br.com.jbugbrasil.urbandictionary.client.helper.CustomTermResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by fspolti on 5/12/17.
 */
public class UrbanDictionaryTest {

    @Test
    public void testUbClientBuilderDefaultValues() {
        UrbanDictionaryClient client = new UrbanDictionaryClientBuilder().term("lol").build();
        Assert.assertEquals("lol", client.getTerm());
        Assert.assertEquals(1, client.getNumberOfResults());
    }

    @Test
    public void testUbClientBuilder() {
        UrbanDictionaryClient client = new UrbanDictionaryClientBuilder().term("rofl").numberOfResults(2).showExample().build();
        Assert.assertEquals("rofl", client.getTerm());
        Assert.assertEquals(2, client.getNumberOfResults());
        Assert.assertEquals(true, client.isShowExample());
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testNullTerm() {
        UrbanDictionaryClient o = new UrbanDictionaryClientBuilder().build();
    }


    @Test
    public void testDefaultNumberOfresults() throws UnsupportedEncodingException {
        UrbanDictionaryClient client = new UrbanDictionaryClientBuilder().term("lol").build();
        Assert.assertEquals(1, client.execute().size());
    }

    @Test
    public void testNumberOfresultsTo1() throws UnsupportedEncodingException {
        UrbanDictionaryClient client = new UrbanDictionaryClientBuilder().term("lol").numberOfResults(1).build();
        Assert.assertEquals(1, client.execute().size());
    }

    @Test
    public void testNumberOfresultsTo2() throws UnsupportedEncodingException {
        UrbanDictionaryClient client = new UrbanDictionaryClientBuilder().term("lol").numberOfResults(2).build();
        Assert.assertEquals(2, client.execute().size());
    }

    @Test
    public void testShowExample() throws UnsupportedEncodingException {
        UrbanDictionaryClient client = new UrbanDictionaryClientBuilder().term("lol").numberOfResults(1).showExample().build();
        client.execute().stream().forEach(e -> {
            Assert.assertTrue(e.getExample() != null);
        });
    }

    @Test
    public void testTermWithSpecialCharacters () throws UnsupportedEncodingException {
        UrbanDictionaryClient client = new UrbanDictionaryClientBuilder().term("¯\\_(ツ)_/¯").build();
        List<CustomTermResponse> response =  client.execute();
        Assert.assertEquals("y u mad", response.get(0).getDefinition());
    }
}
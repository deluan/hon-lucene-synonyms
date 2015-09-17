package org.apache.solr.search;

import org.apache.solr.SolrTestCaseJ4;
import org.junit.BeforeClass;
import org.junit.Test;

public class SynonymExpandingExtendedDismaxQParserPluginTest extends SolrTestCaseJ4 {
    static String rh = "redirectRequestHandler";

    @BeforeClass
    public static void beforeClass() throws Exception {
        initCore("solrconfig.xml", "schema.xml", "src/test/resources/solr");
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        assertU(adoc("id", "0", "name", "This is a swimsuit"));
        assertU(adoc("id", "1", "name", "And this is a swimwear"));
        assertU(adoc("id", "2", "name", "the board shorts are also welcome"));
        assertU(commit());
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        assertU(delQ("*:*"));
        optimize();
        assertU((commit()));

    }

    @Test
    public void shouldMatchOneWordSynonym() throws Exception {
        assertQ(req("qt", "dismax", "q", "swimsuit"),
                "//result[@numFound='3']");
    }

    @Test
    public void shouldMatchTwoWordSynonym() throws Exception {
        assertQ(req("qt", "dismax", "q", "board shorts"),
                "//result[@numFound='3']");
    }
}

package org.jboss.jdf.stacks.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import junit.framework.Assert;

import org.jboss.jdf.stacks.model.Stacks;
import org.jboss.jdf.stacks.parser.Parser;
import org.junit.Test;

import static org.jboss.jdf.stacks.client.StacksClientConfiguration.PRESTACKS_REPO_PROPERTY;

public class ParserTest {

    @Test
    public void test() throws FileNotFoundException {
        InputStream is = this.getClass().getResourceAsStream("/stacks.yaml");
        Parser p = new Parser();
        Stacks stacks = p.parse(is);
        Assert.assertEquals(stacks.getAvailableBoms().size(), 34);
    }

    @Test
    public void testParsePreStacks() throws IOException {
        StacksClient sc = new StacksClient();
        sc.getActualConfiguration().setUrl(new URL(getPropertyFromConfig(PRESTACKS_REPO_PROPERTY)));
        Stacks preStacks = sc.getStacks();
        Assert.assertEquals(preStacks.getAvailableRuntimes().size(), 11);
    }

    private String getPropertyFromConfig(String property) throws IOException {
        InputStream is = null;
        try {
            is = this.getClass().getResourceAsStream("/org/jboss/jdf/stacks/client/config.properties");
            Properties p = new Properties();
            p.load(is);
            return p.getProperty(property);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}

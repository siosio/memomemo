package siosio.it;

import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import org.hamcrest.collection.IsEmptyCollection;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import siosio.memo.MemoForm;
import siosio.memo.MemoResource;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MemoResourceTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "memomemo.war")
                .addAsResource("META-INF/persistence.xml")
                .addPackages(true, "siosio");
    }

    @Test
    public void test(@ArquillianResteasyResource MemoResource resource) throws Exception {
        final List<MemoForm> result = resource.list();

        assertThat(result, IsEmptyCollection.<MemoForm>empty());
    }
}

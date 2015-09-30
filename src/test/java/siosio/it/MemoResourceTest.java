package siosio.it;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.*;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import siosio.entity.MemoEntity;
import siosio.memo.MemoForm;
import siosio.memo.MemoResource;
import siosio.service.MemoService;

import org.junit.After;
import org.junit.Before;
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

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;

    @Before
    public void setUp() throws Exception {
        utx.begin();
        final Query query = em.createNativeQuery("delete from MEMO");
        query.executeUpdate();
        utx.commit();
    }

    @After
    public void tearDown() throws Exception {
        try {
            utx.rollback();
        } catch (Exception ignore) {
            // nop
        }
    }

    @Test
    public void memoNotFound_returnedEmptyList(@ArquillianResteasyResource MemoResource resource) throws Exception {
        final List<MemoForm> result = resource.list();

        assertThat(result, IsEmptyCollection.<MemoForm>empty());
    }

    @Test
    public void memoFound_returnedList(@ArquillianResteasyResource MemoResource resource) throws Exception {
        utx.begin();
        em.persist(new MemoEntity("title", "description"));
        em.persist(new MemoEntity("title2", "description2"));
        utx.commit();

        final List<MemoForm> list = resource.list();
        assertThat("2件取得できること", list, hasSize(2));
        assertThat(list.stream().map(MemoForm::getTitle).collect(toList()), containsInAnyOrder("title", "title2"));
    }
}

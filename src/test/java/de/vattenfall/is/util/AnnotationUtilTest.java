
package de.vattenfall.is.util;

import de.vattenfall.is.util.model.EmptyClass;
import de.vattenfall.is.util.model.TestAnno;
import de.vattenfall.is.util.model.TestAnno2;
import de.vattenfall.is.util.model.MyTestClass;
import de.vattenfall.is.util.model.UnusedAnno;
import java.lang.reflect.Field;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AnnotationUtilTest {

    @Test
    public void getFields() throws Exception {
        Collection<Field> result = AnnotationUtil.getFields(TestAnno.class, MyTestClass.class);
        assertThat(result.size(), is(4));

        result = AnnotationUtil.getFields(TestAnno2.class, MyTestClass.class);
        assertThat(result.size(), is(1));

        result = AnnotationUtil.getFields(UnusedAnno.class, MyTestClass.class);
        assertThat(result.size(), is(0));

        result = AnnotationUtil.getFields(UnusedAnno.class, EmptyClass.class);
        assertThat(result.size(), is(0));
    }
}
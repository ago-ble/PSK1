package lt.vu.services;

import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RUNTIME)
@Documented
public @interface Production {
}

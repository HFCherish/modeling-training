package com.thoughtworks.mobileCharge.support;

import com.google.inject.AbstractModule;
import com.mongodb.client.MongoDatabase;
import org.junit.rules.TestRule;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class DatabaseTestRunner extends InjectBasedRunner {
//    @Inject
//    private SqlSessionManager sqlSessionManager;
    @Inject
    MongoDatabase db;

    public DatabaseTestRunner(final Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected List<AbstractModule> getModules() {
        return asList(new AbstractModule() {
            @Override
            protected void configure() {;
            }
        });
    }

    private final TestRule rollbackSessionManager = (base, description) -> new Statement() {
        @Override
        public void evaluate() throws Throwable {
//            sqlSessionManager.startManagedSession();
            try {
                base.evaluate();
            } finally {
//                try {
////                    sqlSessionManager.rollback(true);
//                } finally {
////                    sqlSessionManager.close();
//                }
                db.getCollection("tests").drop();
            }
        }
    };

    @Override
    protected List<TestRule> getTestRules(Object target) {
        List<TestRule> rules = new ArrayList<>();
        rules.add(rollbackSessionManager);
        rules.addAll(super.getTestRules(target));
        return rules;
    }
}

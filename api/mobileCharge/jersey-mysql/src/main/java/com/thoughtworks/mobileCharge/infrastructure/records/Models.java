package com.thoughtworks.mobileCharge.infrastructure.records;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.thoughtworks.mobileCharge.api.services.CallRecordQueryService;
import com.thoughtworks.mobileCharge.domain.test.MyTestCodecRepo;
import com.thoughtworks.mobileCharge.domain.test.TestRepo;
import com.thoughtworks.mobileCharge.domain.user.UserRepo;
import com.thoughtworks.mobileCharge.infrastructure.mappers.CallRecordMapper;
import com.thoughtworks.mobileCharge.infrastructure.mappers.MyTestCodecMapper;
import com.thoughtworks.mobileCharge.infrastructure.mappers.MyTestMapper;
import com.thoughtworks.mobileCharge.infrastructure.mappers.UserMapper;
import com.thoughtworks.mobileCharge.infrastructure.mongo.CallRecordDB;
import com.thoughtworks.mobileCharge.infrastructure.mongo.MyTestCodecDB;
import com.thoughtworks.mobileCharge.infrastructure.mongo.MyTestDB;
import com.thoughtworks.mobileCharge.infrastructure.mongo.UserDB;
import com.thoughtworks.mobileCharge.infrastructure.mongo.codecs.MyTestCodec;
import com.thoughtworks.mobileCharge.infrastructure.repositories.MyTestCodecCodecRepoImpl;
import com.thoughtworks.mobileCharge.infrastructure.repositories.TestRepoImpl;
import com.thoughtworks.mobileCharge.infrastructure.repositories.UserRepoImpl;
import com.thoughtworks.mobileCharge.infrastructure.services.CallRecordQueryServicesImpl;
import com.thoughtworks.mobileCharge.infrastructure.util.SafetyInjector;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.Properties;

public class Models extends AbstractModule {
//    private static final String DEFAULT_CONFIG_RESOURCE = "mybatis/config.xml";

//    private final String classPathResource;

    private final String environmentId;

    private final Properties properties;

//    private ClassLoader resourcesClassLoader = getDefaultClassLoader();

//    private ClassLoader driverClassLoader = getDefaultClassLoader();

    public Models(String environment) {
        this(environment, new Properties());
    }

    public Models(String environment, Properties properties) {
//        this(environment, DEFAULT_CONFIG_RESOURCE, properties);
        this.environmentId = environment;
        this.properties = properties;
    }

//    public Models(String environment, String classPathResource, Properties properties) {
//        this.environmentId = environment;
////        this.classPathResource = classPathResource;
//        this.properties = properties;
//    }

    @Override
    protected void configure() {
//        bindPersistence();
        String dbname = System.getenv().getOrDefault("MONGODB_DATABASE", "mongodb_store");
        String host = System.getenv().getOrDefault("MONGODB_HOST", "127.0.0.1");
        String port = System.getenv().getOrDefault("MONGODB_PORT", "27017");
        String username = System.getenv().getOrDefault("MONGODB_USER", "admin");
        String password = System.getenv().getOrDefault("MONGODB_PASS", "mypass");
        String connectURL = String.format(
                "mongodb://%s:%s@%s:%s/%s",
                username,
                password,
                host,
                port,
                dbname
        );
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromCodecs(new MyTestCodec(MongoClient.getDefaultCodecRegistry().get(Document.class))));
//        MongoClientOptions.Builder builder = MongoClientOptions.builder().codecRegistry(codecRegistry);

//        mongoClient = new MongoClient(new MongoClientURI(connectURL, builder));
        MongoClient mongoClient = new MongoClient(new MongoClientURI(connectURL));
        MongoDatabase db = mongoClient.getDatabase(dbname).withCodecRegistry(codecRegistry);
        bind(MongoDatabase.class).toInstance(db);
        requestStaticInjection(SafetyInjector.class);

        bind(TestRepo.class).to(TestRepoImpl.class);
        bind(MyTestMapper.class).to(MyTestDB.class);
        bind(MyTestCodecRepo.class).to(MyTestCodecCodecRepoImpl.class);
        bind(MyTestCodecMapper.class).to(MyTestCodecDB.class);

        bind(UserRepo.class).to(UserRepoImpl.class);
        bind(UserMapper.class).to(UserDB.class);
        bind(CallRecordMapper.class).to(CallRecordDB.class);
        bind(CallRecordQueryService.class).to(CallRecordQueryServicesImpl.class);

    }

//    private void bindPersistence() {
//        try {
//            bindSqlManager();
//            bindTransactionalInterceptor();
//            bindSqlSessionFactory();
//            bind(ClassLoader.class)
//                    .annotatedWith(named("JDBC.driverClassLoader"))
//                    .toInstance(driverClassLoader);
//        } finally {
//            resourcesClassLoader = getDefaultClassLoader();
//            driverClassLoader = getDefaultClassLoader();
//        }
//    }

//    private void bindSqlSessionFactory() {
//        try (Reader reader = getResourceAsReader(getResourceClassLoader(), classPathResource)) {
//            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader,
//                    environmentId,
//                    properties);
//            bind(SqlSessionFactory.class).toInstance(sessionFactory);
//
//            Configuration configuration = sessionFactory.getConfiguration();
//
//            bindObjectFactory(configuration);
//            bindMappers(configuration);
//            bindTypeHandlers(configuration);
//            bindInterceptors(configuration);
//        } catch (Exception e) {
//            addError("Impossible to read classpath resource '%s', see nested exceptions: %s",
//                    classPathResource,
//                    e.getMessage());
//            e.printStackTrace();
//        }
//    }

//    private void bindObjectFactory(Configuration configuration) {
//        requestInjection(configuration.getObjectFactory());
//    }

//    private void bindInterceptors(Configuration configuration) {
//        Collection<Interceptor> interceptors = configuration.getInterceptors();
//        for (Interceptor interceptor : interceptors) {
//            requestInjection(interceptor);
//        }
//    }

//    private void bindTypeHandlers(Configuration configuration) {
//        Collection<TypeHandler<?>> allTypeHandlers = configuration.getTypeHandlerRegistry().getTypeHandlers();
//        for (TypeHandler<?> handler : allTypeHandlers) {
//            requestInjection(handler);
//        }
//    }

//    private void bindMappers(Configuration configuration) {
//        Collection<Class<?>> mapperClasses = configuration.getMapperRegistry().getMappers();
//        for (Class<?> mapperType : mapperClasses) {
//            bindMapper(mapperType);
//        }
//    }

//    private void bindTransactionalInterceptor() {
//        TransactionalMethodInterceptor interceptor = new TransactionalMethodInterceptor();
//        requestInjection(interceptor);
//        bindInterceptor(any(), annotatedWith(Transactional.class), interceptor);
//        bindInterceptor(annotatedWith(Transactional.class), not(annotatedWith(Transactional.class)), interceptor);
//    }

//    private void bindSqlManager() {
//        bind(SqlSessionManager.class).toProvider(SqlSessionManagerProvider.class).in(Scopes.SINGLETON);
//        bind(SqlSession.class).to(SqlSessionManager.class).in(Scopes.SINGLETON);
//    }

//    final <T> void bindMapper(Class<T> mapperType) {
//        bind(mapperType).toProvider(guicify(new MapperProvider<T>(mapperType))).in(Scopes.SINGLETON);
//    }

//    protected final ClassLoader getResourceClassLoader() {
//        return resourcesClassLoader;
//    }

//    private ClassLoader getDefaultClassLoader() {
//        return getClass().getClassLoader();
//    }
}


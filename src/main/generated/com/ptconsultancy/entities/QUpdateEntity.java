package com.ptconsultancy.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUpdateEntity is a Querydsl query type for UpdateEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUpdateEntity extends EntityPathBase<UpdateEntity> {

    private static final long serialVersionUID = 1757878889L;

    public static final QUpdateEntity updateEntity = new QUpdateEntity("updateEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath details = createString("details");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath tags = createString("tags");

    public final StringPath title = createString("title");

    public final StringPath username = createString("username");

    public QUpdateEntity(String variable) {
        super(UpdateEntity.class, forVariable(variable));
    }

    public QUpdateEntity(Path<? extends UpdateEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUpdateEntity(PathMetadata metadata) {
        super(UpdateEntity.class, metadata);
    }

}


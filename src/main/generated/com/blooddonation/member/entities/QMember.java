package com.bloodDonation.member.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1357030654L;

    public static final QMember member = new QMember("member1");

    public final com.bloodDonation.commons.entities.QBase _super = new com.bloodDonation.commons.entities.QBase(this);

    public final StringPath address = createString("address");

    public final StringPath addressSub = createString("addressSub");

    public final ListPath<Authorities, QAuthorities> authorities = this.<Authorities, QAuthorities>createList("authorities", Authorities.class, QAuthorities.class, PathInits.DIRECT2);

    public final StringPath bldType = createString("bldType");

    public final StringPath bldType2 = createString("bldType2");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final StringPath gid = createString("gid");

    public final StringPath mName = createString("mName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath userId = createString("userId");

    public final NumberPath<Long> userNo = createNumber("userNo", Long.class);

    public final StringPath userPw = createString("userPw");

    public final StringPath zonecode = createString("zonecode");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}


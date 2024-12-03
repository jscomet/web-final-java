create table assignment
(
    assignment_id bigint auto_increment comment '作业ID'
        primary key,
    course_id     bigint                              null comment '课程ID',
    title         varchar(100)                        not null comment '作业标题',
    description   text                                null comment '作业描述',
    deadline      datetime                            not null comment '截止时间',
    create_time   timestamp default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '作业';

create table assignment_submission
(
    submission_id bigint auto_increment comment '提交ID'
        primary key,
    assignment_id bigint                              null comment '作业ID',
    student_id    bigint                              null comment '学生ID',
    status        int       default 0                 null comment '状态：0-未提交、1-未批改、2-已批改',
    content       text                                null comment '提交内容',
    file_url      varchar(255)                        null comment '文件URL',
    score         float                               null comment '得分',
    feedback      text                                null comment '反馈内容',
    submit_time   timestamp default CURRENT_TIMESTAMP null comment '提交时间'
)
    comment '作业提交';

create table course
(
    course_id      bigint auto_increment comment '课程ID'
        primary key,
    title          varchar(100)                         not null comment '课程标题',
    description    text                                 null comment '课程描述',
    category_id    bigint                               null comment '分类ID',
    teacher_id     bigint                               null comment '教师ID',
    cover_image    varchar(255)                         null comment '封面图片URL',
    is_recommended tinyint(1) default 0                 null comment '是否推荐',
    status         int        default 0                 null comment '课程状态：0-草稿、1-已发布、2-已归档',
    allow_comment  tinyint(1) default 1                 null comment '是否允许评论',
    allow_notes    tinyint(1) default 1                 null comment '是否允许笔记',
    view_count     int        default 0                 null comment '浏览次数',
    like_count     int        default 0                 null comment '点赞数',
    student_count  int        default 0                 null comment '学生数量',
    create_time    timestamp  default CURRENT_TIMESTAMP null comment '创建时间',
    update_time    timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '课程信息';

create table course_carousel
(
    carousel_id bigint auto_increment comment '轮播图ID'
        primary key,
    course_id   bigint                               null comment '课程ID',
    image_url   varchar(255)                         not null comment '图片URL',
    sort_order  int        default 0                 null comment '排序顺序',
    status      tinyint(1) default 1                 null comment '是否启用',
    create_time timestamp  default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '课程轮播图';

create table course_category
(
    category_id bigint auto_increment comment '分类ID'
        primary key,
    name        varchar(50)                         not null comment '分类名称',
    sort_order  int       default 0                 null comment '排序顺序',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '课程分类';

create table course_enrollment
(
    enrollment_id bigint auto_increment comment '注册ID'
        primary key,
    course_id     bigint                              null comment '课程ID',
    student_id    bigint                              null comment '学生ID',
    status        int       default 0                 null comment '状态：0-进行中、1-已完成、2-已退出',
    progress      float     default 0                 null comment '学习进度',
    create_time   timestamp default CURRENT_TIMESTAMP null comment '注册时间'
)
    comment '课程注册';

create table course_material
(
    material_id bigint auto_increment comment '课件ID'
        primary key,
    course_id   bigint                              null comment '课程ID',
    title       varchar(100)                        not null comment '课件标题',
    type        int                                 not null comment '课件类型：0-文档、1-视频、2-音频、3-图片',
    content_url varchar(255)                        not null comment '内容URL',
    sort_order  int       default 0                 null comment '排序顺序',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '课件资料';

create table discussion
(
    discussion_id bigint auto_increment comment '讨论ID'
        primary key,
    course_id     bigint                              null comment '课程ID',
    user_id       bigint                              null comment '用户ID',
    content       text                                not null comment '讨论内容',
    parent_id     bigint                              null comment '父评论ID',
    create_time   timestamp default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '讨论区';

create table question_bank
(
    question_id    bigint auto_increment comment '题目ID'
        primary key,
    creator_id     bigint                              null comment '创建者ID',
    type           int                                 not null comment '题目类型：0-单选、1-多选、2-判断、3-填空、4-问答',
    question_text  text                                not null comment '题目内容',
    options        json                                null comment '选项JSON',
    correct_answer text                                null comment '正确答案',
    create_time    timestamp default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '题库';

create table role
(
    role_id int auto_increment comment '用户id'
        primary key,
    cname   varchar(50) null comment '角色中文名称',
    ename   varchar(50) null comment '角色英文名称'
)
    comment '角色';

create table self_test
(
    test_id     bigint auto_increment comment '试卷ID'
        primary key,
    creator_id  bigint                              null comment '创建者ID',
    title       varchar(100)                        not null comment '试卷标题',
    description text                                null comment '试卷说明',
    settings    json                                null comment '试卷设置JSON',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '自测试卷';

create table study_note
(
    note_id     bigint auto_increment comment '笔记ID'
        primary key,
    course_id   bigint                              null comment '课程ID',
    student_id  bigint                              null comment '学生ID',
    content     text                                not null comment '笔记内容',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间'
)
    comment '学习笔记';

create table test_question
(
    test_id     bigint        not null comment '试卷ID',
    question_id bigint        not null comment '题目ID',
    sort_order  int default 0 null comment '题目顺序',
    primary key (test_id, question_id)
)
    comment '试卷题目关联表';

create table test_record
(
    record_id     bigint auto_increment comment '记录ID'
        primary key,
    test_id       bigint                              null comment '试卷id',
    student_id    bigint                              null comment '学生ID',
    status        int       default 0                 null comment '测试状态 0 未完成 1 已完成',
    answers       json                                null comment '答案JSON',
    score         float                               null comment '得分',
    complete_time timestamp default CURRENT_TIMESTAMP null comment '完成时间'
)
    comment '考试记录';

create table user
(
    user_id         bigint auto_increment comment '用户ID'
        primary key,
    username        varchar(50)                         not null comment '用户名',
    password        varchar(255)                        not null comment '密码',
    salt            varchar(50)                         not null comment '密码盐值',
    email           varchar(100)                        null comment '邮箱',
    student_id      varchar(50)                         null comment '学号',
    nickname        varchar(50)                         null comment '昵称',
    avatar_url      varchar(255)                        null comment '头像URL',
    signature       text                                null comment '个性签名',
    status          int       default 0                 null comment '账号状态：0-活跃、1-禁用、2-已删除',
    login_attempts  int       default 0                 null comment '登录尝试次数',
    last_login_time datetime                            null comment '最后登录时间',
    last_login_ip   varchar(45)                         null comment '最后登录IP',
    create_time     timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_time     timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    gender          int       default 0                 not null comment '性别 0 未知 1 男  2 女',
    constraint email
        unique (email),
    constraint student_id
        unique (student_id),
    constraint username
        unique (username)
)
    comment '用户';

create table user_role
(
    user_id bigint not null comment '用户id',
    role_id int    not null comment '角色id',
    primary key (user_id, role_id)
);



INSERT INTO role (role_id, cname, ename) VALUES (1, '超级管理员', 'SUPER_ADMIN');
INSERT INTO role (role_id, cname, ename) VALUES (2, '老师', 'TEACHER');
INSERT INTO role (role_id, cname, ename) VALUES (3, '学生', 'STUDENT');
INSERT INTO role (role_id, cname, ename) VALUES (4, '游客', 'GUEST');

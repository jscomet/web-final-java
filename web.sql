
-- 用户表
CREATE TABLE user (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    salt VARCHAR(50) NOT NULL COMMENT '密码盐值',
    email VARCHAR(100) UNIQUE COMMENT '邮箱',
    role_id INT NOT NULL COMMENT '角色ID',
    student_id VARCHAR(50) UNIQUE COMMENT '学号',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar_url VARCHAR(255) COMMENT '头像URL',
    signature TEXT COMMENT '个性签名',
    status INT DEFAULT 0 COMMENT '账号状态：0-活跃、1-禁用、2-已删除',
    login_attempts INT DEFAULT 0 COMMENT '登录尝试次数',
    last_login_time DATETIME COMMENT '最后登录时间',
    last_login_ip VARCHAR(45) COMMENT '最后登录IP',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '用户';
-- 角色表
create table role
(
    role_id int auto_increment comment '用户id'
        primary key,
    cname   varchar(50) null comment '角色中文名称',
    ename   varchar(50) null comment '角色英文名称'
)
    comment '角色';
-- 用户角色表
create table user_role
(
    user_id int         not null comment '用户id',
    role_id int         not null comment '角色id',
    primary key (user_id, role_id)
);
-- 课程分类表
CREATE TABLE course_category (
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '课程分类';

-- 课程表
CREATE TABLE course (
    course_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '课程ID',
    title VARCHAR(100) NOT NULL COMMENT '课程标题',
    description TEXT COMMENT '课程描述',
    category_id BIGINT COMMENT '分类ID',
    teacher_id BIGINT COMMENT '教师ID',
    cover_image VARCHAR(255) COMMENT '封面图片URL',
    is_recommended BOOLEAN DEFAULT FALSE COMMENT '是否推荐',
    status INT DEFAULT 0 COMMENT '课程状态：0-草稿、1-已发布、2-已归档',
    allow_comment BOOLEAN DEFAULT TRUE COMMENT '是否允许评论',
    allow_notes BOOLEAN DEFAULT TRUE COMMENT '是否允许笔记',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    student_count INT DEFAULT 0 COMMENT '学生数量',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '课程信息';

-- 课程轮播图表
CREATE TABLE course_carousel (
    carousel_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '轮播图ID',
    course_id BIGINT COMMENT '课程ID',
    image_url VARCHAR(255) NOT NULL COMMENT '图片URL',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    status BOOLEAN DEFAULT TRUE COMMENT '是否启用',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '课程轮播图';

-- 课件表
CREATE TABLE course_material (
    material_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '课件ID',
    course_id BIGINT COMMENT '课程ID',
    title VARCHAR(100) NOT NULL COMMENT '课件标题',
    type INT NOT NULL COMMENT '课件类型：0-文档、1-视频、2-音频、3-图片',
    content_url VARCHAR(255) NOT NULL COMMENT '内容URL',
    sort_order INT DEFAULT 0 COMMENT '排序顺序',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '课件资料';

-- 课程注册表
CREATE TABLE course_enrollment (
    enrollment_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '注册ID',
    course_id BIGINT COMMENT '课程ID',
    student_id BIGINT COMMENT '学生ID',
    status INT DEFAULT 0 COMMENT '状态：0-进行中、1-已完成、2-已退出',
    progress FLOAT DEFAULT 0 COMMENT '学习进度',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间'
) COMMENT '课程注册';

-- 作业表
CREATE TABLE assignment (
    assignment_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '作业ID',
    course_id BIGINT COMMENT '课程ID',
    title VARCHAR(100) NOT NULL COMMENT '作业标题',
    description TEXT COMMENT '作业描述',
    deadline DATETIME NOT NULL COMMENT '截止时间',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '作业';

-- 作业提交表
CREATE TABLE assignment_submission (
    submission_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '提交ID',
    assignment_id BIGINT COMMENT '作业ID',
    student_id BIGINT COMMENT '学生ID',
    content TEXT COMMENT '提交内容',
    file_url VARCHAR(255) COMMENT '文件URL',
    score FLOAT COMMENT '得分',
    feedback TEXT COMMENT '反馈内容',
    submit_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间'
) COMMENT '作业提交';

-- 讨论区表
CREATE TABLE discussion (
    discussion_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '讨论ID',
    course_id BIGINT COMMENT '课程ID',
    user_id BIGINT COMMENT '用户ID',
    content TEXT NOT NULL COMMENT '讨论内容',
    parent_id BIGINT COMMENT '父评论ID',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '讨论区';

-- 学习笔记表
CREATE TABLE study_note (
    note_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '笔记ID',
    course_id BIGINT COMMENT '课程ID',
    student_id BIGINT COMMENT '学生ID',
    content TEXT NOT NULL COMMENT '笔记内容',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '学习笔记';

-- 题库表
CREATE TABLE question_bank (
    question_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '题目ID',
    creator_id BIGINT COMMENT '创建者ID',
    type INT NOT NULL COMMENT '题目类型：0-单选、1-多选、2-判断、3-填空、4-问答',
    question_text TEXT NOT NULL COMMENT '题目内容',
    options JSON COMMENT '选项JSON',
    correct_answer TEXT COMMENT '正确答案',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '题库';

-- 自测试卷表
CREATE TABLE self_test (
    test_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '试卷ID',
    creator_id BIGINT COMMENT '创建者ID',
    title VARCHAR(100) NOT NULL COMMENT '试卷标题',
    description TEXT COMMENT '试卷说明',
    settings JSON COMMENT '试卷设置JSON',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '自测试卷';

-- 试卷题目关联表
CREATE TABLE test_question (
    test_id BIGINT COMMENT '试卷ID',
    question_id BIGINT COMMENT '题目ID',
    sort_order INT DEFAULT 0 COMMENT '题目顺序',
    PRIMARY KEY (test_id, question_id)
) COMMENT '试卷题目关联表';

-- 考试记录表
CREATE TABLE test_record (
    record_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '记录ID',
    test_id BIGINT COMMENT '试卷ID',
    student_id BIGINT COMMENT '学生ID',
    answers JSON COMMENT '答案JSON',
    score FLOAT COMMENT '得分',
    complete_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '完成时间'
) COMMENT '考试记录';
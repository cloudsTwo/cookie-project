<template>
    <el-container class="all">
        <el-header class="head" >
            <el-row :gutter="20" class="row" style="margin-top:11px" v-if="form">
                <el-col :span="3" :offset="17"><i class="fa fa-user-circle" aria-hidden="true"
                        style="margin-top:13px"></i>欢迎登录:{{user.name}}</el-col>
                <el-col :span="2" style="margin-bottom:10px">
                    <div class="changePwd">
                        <el-button type="text" @click="dialogFormVisible = true"><i class="fa fa-cog"
                                aria-hidden="true"></i>修改密码</el-button>
                        <el-dialog title="修改密码" :visible.sync="dialogFormVisible" :append-to-body="true">
                            <el-form :model="form" :rules="rules" ref="from">
                                <el-form-item label="当前密码" :label-width="formLabelWidth">
                                    <el-input v-model="form.pwd" auto-complete="off" type="password"></el-input>
                                </el-form-item>
                                <el-form-item label="新的密码" :label-width="formLabelWidth">
                                    <el-input v-model="form.npwd" auto-complete="off" type="password" prop="npwd">
                                    </el-input>
                                </el-form-item>
                                <el-form-item label="确认密码" :label-width="formLabelWidth">
                                    <el-input v-model="form.cpwd" auto-complete="off" type="password" prop="cpwd">
                                    </el-input>
                                </el-form-item>
                            </el-form>
                            <div slot="footer" class="dialog-footer">
                                <el-button @click="dialogFormVisible = false">取 消</el-button>
                                <el-button type="primary" @click="dialogFormVisible = false;changePwd()">确 定</el-button>
                            </div>
                        </el-dialog>
                    </div>
                </el-col>
                <el-col :span="2" style="margin-top:11px">
                    <div @click="logOut" class="exit"><i class="fa fa-sign-out" aria-hidden="true"></i>退出登录</div>
                </el-col>
            </el-row>
        </el-header>
        <el-main style="padding:0px">
            <el-row class="tac">
                <el-col :span="3">
                    <el-menu @open="handleOpen" @close="handleClose" style="height:600px"  :router='true'>
                        <el-menu-item index="/home/all">
                            <i class="el-icon-news"></i>
                            <span slot="title">全部食谱</span>
                        </el-menu-item>
                        <el-menu-item index="/home/check">
                            <i class="el-icon-camera"></i>
                            <span slot="title">审核食谱</span>
                        </el-menu-item>
                        <el-menu-item index="/home/comment">
                            <i class="el-icon-edit"></i>
                            <span slot="title">全部评论</span>
                        </el-menu-item>
                        <el-menu-item index="/home/user">
                            <i class="el-icon-s-custom"></i>
                            <span slot="title">用户管理</span>
                        </el-menu-item>
                        <el-menu-item index="/home/order" >
                            <i class="el-icon-tickets"></i>
                            <span slot="title">订单管理</span>
                        </el-menu-item>
                        <el-menu-item index="/home/good" >
                            <i class="el-icon-goods"></i>
                            <span slot="title">商品管理</span>
                        </el-menu-item>
                    </el-menu>
                </el-col>
                <el-col class="right">
                    <router-view></router-view>
                </el-col>
            </el-row>
        </el-main>
    </el-container>
</template>

<script>
import { changePassword } from '../network/user';
    export default {
        methods: {
            logOut() {
                this.$router.replace('/login')
            },
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            },
            changePwd() {
                if(this.form.pwd != this.user.password){
                    this.$message({
                        type:'warning',
                        message:'原密码不正确'
                    })
                    return
                }else if(this.form.npwd != this.form.cpwd){
                    this.$message({
                        type:'warning',
                        message:'两次输入密码不一致'
                    })
                    return
                }else{
                    changePassword(this.user.uid, this.form.npwd).then(res => {
                        this.$message({
                            type:'success',
                            message:'修改密码成功'
                        })
                    })
                }
            },
        },
        created() {
            // 在页面加载时读取sessionStorage
            if (sessionStorage.getItem('store')) {
                this.$store.replaceState(Object.assign({}, this.$store.state, JSON.parse(sessionStorage.getItem('store'))))
            }
            this.user = this.$store.state.user

            // 在页面刷新时将store保存到sessionStorage里
            window.addEventListener('beforeunload', () => {
                sessionStorage.setItem('store', JSON.stringify(this.$store.state))
            })
        },
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.form.cpwd !== '') {
                        this.$refs.form.validateField('cpwd');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.form.npwd) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                dialogFormVisible: false,
                form: {
                    pwd: '',
                    npwd: '',
                    cpwd: ''
                },
                rules: {
                    npwd: [{
                        validator: validatePass,
                        trigger: 'blur'
                    }],
                    cpwd: [{
                        validator: validatePass2,
                        trigger: 'blur'
                    }],
                },
                formLabelWidth: '80px',
                authorith:false,
                user: {},
            }
        },
    }
</script>
<style lang="stylus" scoped>
    .head 
        background-image url('../assets/geometric-leaves.png') 
        background-size: 100% 100%;
        height 100px
        width 100%
        font-size 13px
        font-family "PingFang SC",Georgia,Serif;
        .exit 
            cursor pointer
        .changePwd
            cursor pointer
</style>

<style lang="">
    .all{
        background-color: rgb(251, 252, 241);
    }
    .tac{
        width: 100%;
        overflow-x: hidden;
    }
    .right{
        background-color: white;
        height: 500px;
        width: 1300px;
        position: absolute;
        left: 191px;
    }
</style>
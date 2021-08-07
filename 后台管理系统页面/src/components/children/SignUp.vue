<template>
    <div class="out" v-if="showSignUp">
        <el-container class="cont" style="height:700px">
            <el-main class="main">
                <el-row type="flex" justify="center" align="end" class="row">
                    <el-col :span="6">
                        <el-form ref="ruleForm" :model="ruleForm" label-width="80px" class="form" :rules="rules">
                            <el-form-item label="用户名" prop="name">
                                <el-input v-model="ruleForm.name"></el-input>
                            </el-form-item>
                            <el-form-item label="密码" prop="pwd">
                                <el-input v-model="ruleForm.pwd" type="password"></el-input>
                            </el-form-item>
                            <el-form-item label="确认密码" prop="cpwd">
                                <el-input v-model="ruleForm.cpwd" type="password"></el-input>
                            </el-form-item>
                            <el-form-item label="手机号码" prop="phoneNumber" :rules="[
                                    { required: true, message: '手机号不能为空'},
                                    { type: 'number', message: '手机号必须为数字值'}
                                    ]">
                                <el-input v-model.number="ruleForm.phoneNumber" type="tel"></el-input>
                            </el-form-item>
                            <el-form-item style="padding: 10px">
                                <el-button type="primary" @click="register">注册</el-button>
                                <el-button type="" @click="back">返回</el-button>
                            </el-form-item>
                        </el-form>
                    </el-col>
                </el-row>
            </el-main>
        </el-container>
    </div>
</template>

<script>
import {Registered,findOneUserByName} from '@/network/user.js'
import axios from 'axios'

    export default {
        methods: {
            back() {
                this.$emit('changeLogin')
            },
            register(){
                let reg = /\s+/
                let user = {
                    name: this.ruleForm.name,
                    password:this.ruleForm.pwd,
                    phoneNumber:this.ruleForm.phoneNumber,
                    admin: 1,
                }

                // 验证用户
                for(let name in user){
                    if(user[name] === '' || reg.test(user[name])){
                        alert('请输入'+name)
                        return
                    }
                }

                // Registered(this.user)
                findOneUserByName(user.name).then(res=>{
                    if(res.data != ''){
                        alert('用户名已存在')
                    }else{
                        axios.post('http://localhost:8888/day07/user/registered',user)
                        .then(res=>{
                            alert("注册成功")
                            user={}
                            this.$emit('changeLogin')
                        })
                        .catch(err => {
                            alert("注册失败");
                            console.log(err);
                        })
                    }
                }).catch(err => {
                    console.log(err)
                })
            }
        },
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.ruleForm.cpwd !== '') {
                        this.$refs.ruleForm.validateField('cpwd');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.ruleForm.pwd) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                rules: {
                    name: [{
                        required: true,
                        message: '请输入用户名',
                        trigger: 'blur'
                    }, ],
                    pwd: [{
                        validator: validatePass,
                        trigger: 'blur'
                    }],
                    cpwd: [{
                        validator: validatePass2,
                        trigger: 'blur'
                    }],
                },
                ruleForm: {
                    name: '',
                    pwd: '',
                    cpwd: '',
                    phoneNumber:'',
                },

            }
        },
        props: {
            showSignUp: {
                type: Boolean,
                default: false
            },
        },
    }
</script>

<style lang="stylus" scoped>
        .main
            top 200px
            height 100%
            width 100%
            background-color white
            margin-bottom 400px
            .form 
                background-color white 
                padding-top 50px
                padding-right 50px
                padding-bottom 10px 
                transition: box-shadow 0.5s;
                -webkit-transition: box-shadow 0.5s;
                :hover
                   opacity: 1;
</style>

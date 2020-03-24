---
title: "Centos7"
date: 2020-03-20T23:43:22+08:00
draft: false
tags: ["Linux","centos7"] #标签
categories: ["环境配置"] #分类
featured_image: #顶部图片
description:  #描述
---

# centos7 配置

## 1、软件

- yum install xxx //安装xxx

- yum search xxx //搜索xxx

- yum localinstall xxx.rpm //安装本地xxx(网络不好使)

- 常用的软件包

  - openjdk

  - gitea

  - docker

  - mysql

  - jenkins

  - git

  - zsh

  - nmap (测试某个端口是否开启)  `nmap xxx.xxx.xxx.xxx`

## 2、防火墙

- 开启

```txt
firewall-cmd --zone=public --add-port=5672/tcp --permanent   # 开放5672端口
```

- 关闭

```txt
firewall-cmd --zone=public --remove-port=5672/tcp --permanent  #关闭5672端口
```

- 生效

```txt
firewall-cmd --reload   # 配置立即生效
```

- 查看

```txt
firewall-cmd --zone=public --list-ports //查看开放端口
firewall-cmd --zone=public --list-ports //防火墙状态
netstat -lnpt //查看监听的端口
```

## 3、免密登录

1. 本地机器终端 `ssh-keygen -t rsa`会在.ssh目录下生成 id_rsa 和 id_rsa.pub 俩个文件

2. 将 id_rsa 上传服务器.ssh目录下`scp id_rsa.pub xx.xx.xx.xx:/root/.ssh/`

3. 登录远程主机`ssh root@xx.xx.xx.xx`

4. 进入.ssh目录下 `cat id_rsa.pub >> authorized_keys`

5. 推出远程链接,然后就可以免密登录了

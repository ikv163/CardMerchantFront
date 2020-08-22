# 项目相关说明
## 编译方式
```

开发环境
java -jar -Dspring.profiles.active=dev cardmerchantfront-admin/target/cardmerchantfront-admin.jar
 
测试环境
java -jar -Dspring.profiles.active=fat cardmerchantfront-admin/target/cardmerchantfront-admin.jar 

预发环境
java -jar -Dspring.profiles.active=uat cardmerchantfront-admin/target/cardmerchantfront-admin.jar

生产环境
java -jar -Dspring.profiles.active=prod cardmerchantfront-admin/target/cardmerchantfront-admin.jar 

```

```
文件夹是按数据库名命名的,按顺序执行

├── tyadmin
│   └── tyadmin.add.sql
├── typayv2
│   ├── typayv2.alter_t.sql
│   ├── typayv2.create_t.sql
│   └── typayv2.t_bankcard2020-2-26.sql
└── tysupplieradmin
    ├── tysupplieradmin.sys20200207.sql
    ├── tysupplieradmin.alter_t_20200224.sql
    ├── tysupplieradmin.system_menu.sql
    └── tysupplieradmin.sys_user_smd2020-2-9.sql

```

## testing 
```
http://java-cardmerchantfront.99xyp.com/login

admin/admin123

google code:

IX4ZL6Z6QQMAEZE

```


## jenkins

```shell script
http://jenkins.tianxiapays.com/

zhou zhou123
```

## yearning
```

包网天下支付测试环境yearning地址
http://220.228.174.200:18000/#/login
http://220.228.174.200:8000/#/login
yearning登录
账号：邮箱前缀（例如：邮箱为 jsabc@itcom888.com，则yearning账号为 jsabc）
密码：邮箱密码
登录需勾选 LDAP 

说明：首次登录后需联系dba人员开放相关操作权限
```

## 包网的UAT环境
```
java-cardmerchantfront.tianxiauat.com
```
## 包网的PROD环境
```
tx.8tm212.com 
```

## DBA注意事项

```

sys_oper_log 每个月归档一下

sys_logininfor  每个月归档一下
```

```
测试环境服务器：10.4.20.221


【公司外网IP】  
  12F     
  154.197.20.2
  58.69.2.62
  203.90.239.2   
  安顺  45.195.92.0/27  255.255.255.224  
  PLDT  58.69.2.60/30  255.255.255.252  
  HGC  203.90.239.0/27  255.255.255.224  
  Globe  203.177.132.144/30  255.255.255.252  
  7F      
  运营商  IP  掩码  网关
  MAOZ  172.16.4.12  255.255.255.252  172.16.4.13
  HGHK  203.90.239.66  255.255.255.224  203.90.239.67
  ASGN  14.18.253.226  255.255.255.240  14.18.253.227
  GLGN  183.2.198.72  255.255.255.248  183.2.198.73
  GLHK  160.20.56.16/28  255.255.255.240  160.20.56.17
  FBHK  154.86.5.32  255.255.255.240  154.86.5.33
  FBGN  119.147.218.144  255.255.255.240  119.147.218.145
  HYGN  10.10.222.0  255.255.255.248  10.10.222.1
  AOHK  134.159.84.128/26  255.255.255.192  134.159.84.129
  包网IP：      
  GLHK  103.113.60.128/28  255.255.255.240  103.113.60.129
  GLGN  183.2.198.208/28  255.255.255.240  183.2.198.209
  HGHK  203.90.224.128/28  255.255.255.240  203.90.224.129
    103.113.60.130    
    183.2.198.210    
    203.90.246.130    
    14.18.254.210    
    203.177.135.106    
    203.90.246.133
    
    
  ```

## docker部署redis集群的方式:
    
```bash
docker run -p 7000-7050:7000-7050 -p 5000-5010:5000-5010 -e "IP=0.0.0.0" grokzen/redis-cluster:latest
```
即可现实redis集群

PHP版本:
http://cardmerchantfront.99xyp.com/site/login

Kiddy
   
kiddyadminkiddy
  

J7FIXFMEXA2P7CCS
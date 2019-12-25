#2.4.3 常用快捷键
ctrl+alt+b 当前接口的实现类有哪些<br/>
ctrl+h 打开当前类的实现类窗口<br/>
Ctrl+Alt+M 选中代码抽取为一个方法<br/>
Ctrl+单击方法或类，进入到父类中<br/>
Ctrl+Alt +单击方法或类，进入到子类中<br/>
打开Run Dashboard 集中管理运行的应用： View > Tool Windows >Run Dashboard<br/>
双击 Shift 键，框中直接搜你想搜的类或者方法<br/>
搜索本项目中的方法或者配置信息中的内容 CTRL + Shift + F<br/>
Ctrl+N 输入要搜索的类 ，想搜索的类包括在jar里面，需要勾选“include non-project itms”选项，就可以搜索出来<br/>

#下面是一些gitignore文件的写法分享：<br>
(1)所有空行或者以注释符号 ＃ 开头的行都会被 Git 忽略。 <br>
(2)可以使用标准的 glob 模式匹配。 <br>
(3)匹配模式最后跟反斜杠（/）说明要忽略的是目录。 <br>
(4)要忽略指定模式以外的文件或目录，可以在模式前加上惊叹号（!）取反。 <br>
所谓的 glob 模式是指 shell 所使用的简化了的正则表达式。<br>
星号（*）匹配零个或多个任意字符；<br>
[abc] 匹配任何一个列在方括号中的字符（这个例子要么匹配一个 a，要么匹配一个 b，要么匹配一个 c）；问号（?）只匹配一个任意字符；<br>
如果在方括号中使用短划线分隔两个字符，表示所有在这两个字符范围内的都可以匹配（比如 [0-9] 表示匹配所有 0 到 9 的数字）。<br>

```html

# 此为注释 – 将被 Git 忽略
*.a       # 忽略所有 .a 结尾的文件
!lib.a    # 但 lib.a 除外
/TODO     # 仅仅忽略项目根目录下的 TODO 文件，不包括 subdir/TODO
build/    # 忽略 build/ 目录下的所有文件
doc/*.txt # 会忽略 doc/notes.txt 但不包括 doc/server/arch.txt

```
**下面有些人会遇到加上这个文件，发现忽略的文件还是有上传。**<br>
**原因：** <br>
在git库中已存在了这个文件，之前push提交过该文件。 <br>
.gitignore文件只对还没有加入版本管理的文件起作用，如果之前已经用git把这些文件纳入了版本库，就不起作用了 <br>
**解决：** <br>
需要在git库中删除该文件，并更新。 <br>
然后再次git status查看状态，文件不再显示状态。<br>
#6.2.1 分析 Remember-Me 实现流程
1. 用户选择了“记住我”成功登录后，将会把username、随机生成的序列号、生成的token存入一个数据库表中，同时将它们的组合生成一个cookie发送给客户端浏览器。
2. 当没有登录的用户访问系统时，首先检查 remember-me 的 cookie 值 ，有则检查其值包含的 username、序列号和 token 与数据库中是否一致，一致则通过验证。
并且系统还会重新生成一个新的 token 替换数据库中对应旧的 token，序列号 series 保持不变 ，同时删除旧
的 cookie，重新生成 cookie 值（新的 token + 旧的序列号 + username）发送给客户端。
3. 如果对应cookie不存在，或者包含的username、序列号和token 与数据库中保存的不一致，那么将会引导用
户到登录页面。
因为cookie被盗用后还可以在用户下一次登录前顺利的进行登录，所以如果你的应用对安全性要求比较高就
不要使用Remember-Me功能。
#6.2.2实现用户名密码 Remember-Me 功能 
*连接mysql数据库相关的依赖*
1. mengxuegu-security-core 的 pom.xml 引入依赖
2. mengxuegu-security-web 的 application.yml 配置数据源
3. 安全配置
4. 分析 Remember-Me 底层源码实现

 1) UsernamePasswordAuthenticationFilter 拥有一个 RememberMeServices 的引用，默认是一个空实现的
 NullRememberMeServices ，而实际当我们通过 rememberMe() 启用 Remember-Me 时，它是一个具体的实
 现。
 2) 用户的请求会先通过 UsernamePasswordAuthenticationFilter ，当认证成功后会调用 RememberMeServices 的
 loginSuccess() 方法，否则调用 RememberMeServices 的 loginFail() 方法。
 UsernamePasswordAuthenticationFilter 不会调用 RememberMeServices 的 autoLogin() 方法进行自动登录
 的。
 3) 当执行到 RememberMeAuthenticationFilter 时，如果检测到还没有认证成功时，那么
 RememberMeAuthenticationFilter 会尝试着调用所包含的 RememberMeServices 的 autoLogin() 方法进行自
 动登录。
 4) PersistentTokenBasedRememberMeServices 是 RememberMeServices 的启动Remember-Me 默认实现 , 
 它会通过 cookie 值进行查询数据库存储的记录, 来实现自动登录 ，并重新生成新的 cookie 存储。

#6.3 手机短信验证码认证功能

手机号登录是不需要密码的，通过短信验证码实现免密登录功能。
1. 向手机发送手机验证码，使用第三方短信平台 SDK 发送，如: 阿里云短信服务（阿里大于）
2. 登录表单输入短信验证码
3. 使用自定义过滤器 MobileValidateFilter
4. 当验证码校验通过后，进入自定义手机认证过滤器 MobileAuthenticationFilter 校验手机号是否存在
5. 自定义 MobileAuthenticationToken 提供给  MobileAuthenticationFilter
6. 自定义 MobileAuthenticationProvider 提供给 ProviderManager 处理
7. 创建针对手机号查询用户信息的  MobileUserDetailsService ，交给  MobileAuthenticationProvider
8. 自定义 MobileAuthenticationConfig 配置类将上面组件连接起来，添加到容器中
9. 将 MobileAuthenticationConfig 添加到 SpringSecurityConfig 安全配置的过滤器链上。
####6.3.2 创建短信发送服务接口
####6.3.3 手机登录页与发送短信验证码
####6.3.6 封装手机认证Token MobileAuthenticationToken
####6.3.7 实现手机认证提供者 MobileAuthenticationProvider
####6.3.8 手机号获取用户信息 MobileUserDetailsService
####6.3.9 自定义管理认证配置 MobileAuthenticationConfig
####6.3.10 1重构失败处理器回到手机登录页

##6.4 实现手机登录RememberMe功能

#### 6.4.1 分析实现

1. 在 UsernamePasswordAuthenticationFilter 拥有一个 RememberMeServices 的引用，其实这个接收引用的是
其父抽象类 AbstractAuthenticationProcessingFilter 提供的 setRememberMeServices 方法。
2. 而在实现手机短信验证码登录时，我们自定了一个   MobileAuthenticationFilter   也一样的继承
了   AbstractAuthenticationProcessingFilter 它，我们只要向其 setRememberMeServices 方法手动注入一
个 RememberMeServices 实例即可。
####6.5 获取当前用户认证信息
####6.6 重构实现路径可配置



# "开源中国"项目源码分析：

## net.oschina.app.util.CyptoUtils
加密解密工具包
DES算法

## java.util.Properties

##　android.content.SharedPreferences

## Properties 和 SharedPreferences 如何选用
SharedPreference放在data/data/包名/下面，是占用内存的。
如果保存大量的数据，需要放到sdcard下去，所以SharedPreferences不方便，直接用Properties类的方式比较好。
可以把文件当作字符串传入，能访问获取正确值就好！

## <category android:name="android.intent.category.BROWSABLE" />
* Activities that can be safely invoked from a browser must support this
* category.  For example, if the user is viewing a web page or an e-mail
* and clicks on a link in the text, the Intent generated execute that
* link will require the BROWSABLE category, so that only activities
* supporting this category will be considered as possible actions.  By
* supporting this category, you are promising that there is nothing
* damaging (without user intervention) that can happen by invoking any
* matching Intent.

##　<category android:name="android.intent.category.DEFAULT" />
* Set if the activity should be an option for the default action
* (center press) to perform on a piece of data.  Setting this will
* hide from the user any activities without it set when performing an
* action on some data.  Note that this is normally -not- set in the
* Intent when initiating an action -- it is for use in intent filters
* specified in packages.

 ##  TabSpec
* A tab has a tab indicator, content, and a tag that is used to keep
  * track of it.  This builder helps choose among these options.
    *
  * For the tab indicator, your choices are:
  * 1) set a label
  * 2) set a label and an icon
    *
  * For the tab content, your choices are:
  * 1) the id of a {@link View}
  * 2) a {@link TabContentFactory} that creates the {@link View} content.
  * 3) an {@link Intent} that launches an {@link android.app.Activity}.

# persistent cookies和session cookie

该段逻辑在`AppConfig.java`类中

![2017326-171807](C:/Users/cheng/Desktop/2017326-171807.jpg)

```
Session是由应用服务器维持的一个服务器端的存储空间，用户在连接服务器时，会由服务器生成一个唯一的SessionID,用该SessionID 为标识符来存取服务器端的Session存储空间。而SessionID这一数据则是保存到客户端，用Cookie保存的，用户提交页面时，会将这一 SessionID提交到服务器端，来存取Session数据。这一过程，是不用开发人员干预的。所以一旦客户端禁用Cookie，那么Session也会失效。

服务器也可以通过URL重写的方式来传递SessionID的值，因此不是完全依赖Cookie。如果客户端Cookie禁用，则服务器可以自动通过重写URL的方式来保存Session的值，并且这个过程对程序员透明。

可以试一下，即使不写Cookie，在使用request.getCookies();取出的Cookie数组的长度也是1，而这个Cookie的名字就是JSESSIONID，还有一个很长的二进制的字符串，是SessionID的值。

Cookie是客户端的存储空间，由浏览器来维持。 
在一些投票之类的场合，我们往往因为公平的原则要求每人只能投一票，在一些WEB开发中也有类似的情况，这时候我们通常会使用COOKIE来实现，例如如下的代码： 
cookie[]cookies = request.getCookies(); 
if (cookies.lenght == 0 || cookies == null) 
doStuffForNewbie(); 
//没有访问过 
}
else 
{ 
doStuffForReturnVisitor(); //已经访问过了 
}

这是很浅显易懂的道理，检测COOKIE的存在，如果存在说明已经运行过写入COOKIE的代码了，然而运行以上的代码后，无论何时结果都是执行doStuffForReturnVisitor()，通过控制面板-Internet选项-设置-察看文件却始终看不到生成的cookie文件，奇怪，代码明明没有问题，不过既然有cookie，那就显示出来看看。 
cookie[]cookies = request.getCookies(); 
if (cookies.lenght == 0 || cookies == null) 
out.println("Has not visited this website"); 
}

else 
{ 
for (int i = 0; i < cookie.length; i++) 
{ 
out.println("cookie name:" + cookies[i].getName() + "cookie value:" + 
cookie[i].getValue()); 
} 
}

运行结果: 
cookie name:JSESSIONID cookie value:KWJHUG6JJM65HS2K6

为什么会有cookie呢,大家都知道，http是无状态的协议，客户每次读取web页面时，服务器都打开新的会话，而且服务器也不会自动维护客户的上下文信息，那么要怎么才能实现网上商店中的购物车呢，session就是一种保存上下文信息的机制，它是针对每一个用户的，变量的值保存在服务器端，通过SessionID来区分不同的客户,session是以cookie或URL重写为基础的，默认使用cookie来实现，系统会创造一个名为JSESSIONID的输出cookie，我们叫做session cookie,以区别persistent cookies,也就是我们通常所说的cookie,注意session cookie是存储于浏览器内存中的，并不是写到硬盘上的，这也就是我们刚才看到的JSESSIONID，我们通常情是看不到JSESSIONID的，但是当我们把浏览器的cookie禁止后，web服务器会采用URL重写的方式传递Sessionid，我们就可以在地址栏看到sessionid=KWJHUG6JJM65HS2K6之类的字符串。

明白了原理，我们就可以很容易的分辨出`persistent cookies`和`session cookie`的区别了，网上那些关于两者安全性的讨论也就一目了然了，session cookie针对某一次会话而言，会话结束session cookie也就随着消失了，而persistent cookie只是存在于客户端硬盘上的一段文本（通常是加密的），而且可能会遭到cookie欺骗以及针对cookie的跨站脚本攻击，自然不如session cookie安全了。 
通常session cookie是不能跨窗口使用的，当你新开了一个浏览器窗口进入相同页面时，系统会赋予你一个新的sessionid，这样我们信息共享的目的就达不到了，此时我们可以先把sessionid保存在persistent cookie中，然后在新窗口中读出来，就可以得到上一个窗口SessionID了，这样通过session cookie和persistent cookie的结合我们就实现了跨窗口的session tracking（会话跟踪）。 
在一些web开发的书中，往往只是简单的把Session和cookie作为两种并列的http传送信息的方式，session cookies位于服务器端，persistent cookie位于客户端，可是session又是以cookie为基础的，明白的两者之间的联系和区别，我们就不难选择合适的技术来开发web service了
```
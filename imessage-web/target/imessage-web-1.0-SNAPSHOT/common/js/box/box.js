/**
 * 财付通彩票 - Box模块
 * @author: zawa
 * @date: 2012/09/19
 * 
 * @modify:
 *      2012/09/19 zawa 从彩票API分离出来
 *      
 * @namespace CPBox   
 *      
 */

(function() {
    
    if (typeof CPBox != 'undefined') {
        return;
    }
    
    var _html = document.getElementsByTagName('html')[0],
        _bd = document.getElementsByTagName('body')[0], 
        _ua = navigator.userAgent.toLowerCase(),
        _b = /msie ([\w.]+)/.exec(_ua),
        _isIE = !!_b,
        _isIE6 = _isIE && parseInt(_b[1], 10) == 6,
        _SECURE_PAGE = _isIE ? 'javascript:\'\'' : 'about:blank', // 动态IFRAME时用的安全页
        _css = 'https://wallet.tenpay.com/res/wallet_v2/pay_caipiao/css/cp_box_tip.css?v=20120919';
    
    
    var $ = function(id) {
        return (typeof id == 'string') ? document.getElementById(id) : id;            
    };
    
    /**
     * Event
     */
    var Event = function() {
        var guid = 1,
            randomKey = +new Date();
        
        return {
            /**
             * 添加事件
             * @param {Object} el 对象
             * @param {String} eventName 事件名称(不带on)
             * @param {Function} fn 函数
             */
            addEvent: function() {
                if (window.addEventListener) {
                    return function(el, eventName, fn) {
                        el.addEventListener(eventName, fn, false);
                    };
                } else {
                    return function(el, eventName, fn) {
                        if (!fn.guid) {
                            fn.guid = randomKey + guid;
                            guid++;
                        }
                        el['e' + fn.guid] = fn;
                        el[fn.guid] = function() {el['e' + fn.guid](window.event);};
                        el.attachEvent('on' + eventName, el[fn.guid]);
                    };
                }
            }(),
            /**
             * 移除事件
             * @param {Object} el 对象
             * @param {String} eventName 事件名称(不带on)
             * @param {Function} fn 函数
             */
            removeEvent: function() {
                if (window.removeEventListener) {
                    return function(el, eventName, fn) {
                        el.removeEventListener(eventName, fn, false);
                    };
                } else {
                    return function(el, eventName, fn) {
                        if (fn.guid && el[fn.guid]) {
                            el.detachEvent('on' + eventName, el[fn.guid]);
                            el[fn.guid] = null;
                        } else {
                            el.detachEvent('on' + eventName, fn);
                        }
                    };
                }
            }()
        }
    }();
    
    
    /**
     * Util
     */
    var Util = {
        /**
         * 显示
         * @param {String|Array|Object} id
         * @param {Boolean} [visible:false] 是否为visibility
         * @return {Object} this 可链式操作
         */
        show: function(id, visible) {
            var arrID = [].concat(id),
                o;
            for (var i = 0, len = arrID.length; i < len; i++) {
                o = $(arrID[i]);
                if (o) {
                    if (!visible) {
                        o.style.display = 'block';
                    } else {
                        o.style.visibility = 'visible';
                    }
                }
            }
            return this;
        },
        /**
         * 隐藏
         * @param {String|Array|Object} id
         * @param {Boolean} [visible:false] 是否为visibility
         * @return {Object} this 可链式操作
         */
        hide: function(id, visible) {
            var arrID = [].concat(id),
                o;
            for (var i = 0, len = arrID.length; i < len; i++) {
                o = $(arrID[i]);
                if (o) {
                    if (!visible) {
                        o.style.display = 'none';
                    } else {
                        o.style.visibility = 'hidden';
                    }
                }
            }
            return this;
        }
    };
    
    /**
     * 尺寸
     */
    var Size = {
        /**
         * 获取对象尺寸
         * @param {String|Object} id
         * @return {Array} [width, height]
         */
        getObjSize: function(id) {
            var obj = $(id);
            return [obj.offsetWidth, obj.offsetHeight];
        },
        /**
         * 获取页面实际尺寸
         * @return {Array} [width, height]
         */
        getPageSize: function() {
            return [_html.scrollWidth, _html.scrollHeight];
        },
        /**
         * 获取窗口可视尺寸
         * @return {Array} [width, height]
         */
        getWinSize: function() {
            var winX = document.documentElement.clientWidth || window.innerWidth,
                winY = document.documentElement.clientHeight || window.innerHeight;
            return [winX, winY];
        },
        /**
         * 获取窗口滚动尺寸
         * @return {Array} [水平向左, 垂直向上, 水平all, 垂直all]
         */
        getScrollSize: function() {
            var arrScrollSize = [],
                arrPageSize = this.getPageSize(),
                arrWinSize = this.getWinSize();
            arrScrollSize[0] = window.pageXOffset || document.documentElement.scrollLeft;
            arrScrollSize[1] = window.pageYOffset || document.documentElement.scrollTop;
            arrScrollSize[2] = arrPageSize[0] - arrWinSize[0];
            arrScrollSize[3] = arrPageSize[1] - arrWinSize[1];
            arrScrollSize[2] = (arrScrollSize[2] > 0) ? Math.abs(arrScrollSize[2]) : 0;
            arrScrollSize[3] = (arrScrollSize[3] > 0) ? Math.abs(arrScrollSize[3]) : 0;
            return arrScrollSize;
        },
        /**
         * 获取iframe尺寸
         * @param {String|Object} id
         * @return {Array} [width, height]
         */
        getFrameSize: function(id) {
            var obj = $(id),
                w = obj.contentWindow.document.body.scrollWidth,
                iHeight1 = obj.contentWindow.document.body.scrollHeight,
                iHeight2 = obj.contentWindow.document.documentElement.scrollHeight,
                iBodyHeight = obj.contentWindow.document.getElementsByTagName('body')[0].offsetHeight,
                iHtmlHeight = obj.contentWindow.document.getElementsByTagName('html')[0].offsetHeight,
                h = Math.max(iHeight1, iHeight2, iBodyHeight, iHtmlHeight) + 2; // MSIE 2px 文档边框值(IE6下存在), 由于MSIE并没有0,0的文档起始位置，因此通常会设置2px的边框在周围
            return [w, h];
        }
    };
    
    /**
     * Loader
     */
    var Loader = {
        /**
         * 加载样式
         * @param {String} url
         * @param {String} [charset]
         */
        loadCss: function(url, charset) {
            var css = document.createElement('link');
            css.setAttribute('rel', 'stylesheet');
            css.setAttribute('type', 'text/css');
//            css.setAttribute('charset', charset || 'gb2312');
            css.setAttribute('href', url);
            document.getElementsByTagName('head')[0].appendChild(css);
        },
        /**
         * 创建并加载iframe(若对象已存在则直接加载)
         * @param {Object} o 配置对象
         * {
         *     o.url: {String} iframe地址
         *     [o.id]: {String/Object} 若id指向的对象存在则不创建
         *     [o.css]: {String} 样式名
         *     [o.w]: {Int} iframe宽度
         *     [o.h]: {Int} iframe高度
         *     [o.scroll]: {String} scrolling
         *     [o.fn]: {Function} 回调
         *     [o.target]: {String/Object} 要插入的父节点(创建iframe或指定的iframe不存在时可指定)
         * }
         */
        loadFrame: function(o) {
            var isCeate = (!o.id || !$(o.id)) ? true : false,
                iframe = isCeate ? document.createElement('iframe') : $(o.id),
                target;
            if (o.id) iframe.id = o.id;
            if (o.css) iframe.className = o.css;
            if (o.w) iframe.style.width = (/^\d+(\.\d+)?$/.test(o.w)) ? o.w + 'px' : o.w;
            if (o.h) iframe.style.height = (/^\d+(\.\d+)?$/.test(o.h)) ? o.h + 'px' : o.h;
            if (o.scroll) iframe.scrolling = o.scroll;
            iframe.frameBorder = 0;
            iframe.src = o.url;
            if (_isIE){
                iframe.onreadystatechange = function(){
                    if (iframe.readyState == 'complete') {
                        if (typeof o.fn == 'function') o.fn();
                        iframe.onreadystatechange = null;
                        iframe = null;
                    }
                };
            } else {
                iframe.onload = function() {
                    if (typeof o.fn == 'function') o.fn();
                    iframe.onload = null;
                    iframe = null;
                };
            }
            if (isCeate) {
                target = o.target ? $(o.target) : document.body;
                target.appendChild(iframe);
            }
        }
    };
    
    
    var Box = function() {
        var oBox = null,
            oBoxMask = null,
            iframeTimer = null,
            isBoxOpen = false,
            name = 'CP_Box';
            
        /** 初始化Box */
        var init = function() {
            if (oBox) {
                return;
            }
            // box
            var str = [];
            str.push('<div id="cp_box_inner" class="cp-box">');
            str.push('<a id="cp_box_closer" class="cp-box-closer" href="###" title="关闭"><!--closer--></a>');
            str.push('<div id="cp_box_title" class="cp-box-title"><span id="cp_box_title_main" class="cp-box-title-main"></span></div>');
            str.push('<div id="cp_box_content" class="cp-box-content"><span id="cp_box_icon" class="cp-box-ico"><!--ico--></span><div id="cp_box_content_main"></div></div>');
            str.push('<div id="cp_box_ft" class="cp-box-ft"><span class="cp-box-btn-bt"><input type="button" value="确定" /></span><span class="cp-box-btn-bt"><input type="button" value="取消" /></span></div>');
            str.push('<span class="cp-box-tl"></span><span class="cp-box-tr"></span><span id="cp_box_bl" class="cp-box-bl"></span><span id="cp_box_br" class="cp-box-br"></span>');
            str.push('</div>');
            str.push('<div class="cp-box-shadow"></div>');
            str = str.join('');
            oBox = document.createElement('div');
            oBox.id = 'cp_box';
            oBox.className = 'cp-box-outer';
            oBox.innerHTML = str;
            _bd.appendChild(oBox);
            // mask
            oBoxMask = document.createElement('div');
            oBoxMask.id = 'cp_box_mask';
            oBoxMask.className = 'cp-box-mask';
            if (_isIE6) {
                oBoxMask.innerHTML = '<iframe frameborder="0" src="' + _SECURE_PAGE + '" allowtransparency="true"></iframe>';
            }
            _bd.appendChild(oBoxMask);
        };
        /** 关闭 */
        var close = function() {
            iframeTimer && clearInterval(iframeTimer);
            Util.hide([oBoxMask, oBox]);
            // 销毁onresize事件
            Event.removeEvent(window, 'resize', resize);
            if (_isIE) {
                bugFix.sel(true);
            }
            // 销毁ie6 bug fix事件
            if (_isIE6) {
                Event.removeEvent(window, 'scroll', bugFix.fixed);
            }
            isBoxOpen = false;
            return false;
        };
        /**
         * 设置
         * @param {Object} o 配置
         * @param {String} scene 使用场景
         */
        var setter = function(o, scene) {
            name = o.name || 'CP_Box';
            // 设置自定义样式,窗口规范
            var boxMode = ['cp-box-outer'];
            // 是否固定(IE6不支持fixed)
            oBox.style.position = (o.fixed === false || _isIE6) ? 'absolute' : 'fixed';
            // 透明度外边框
            var border = 20; // 透明边框+border值:9+1+1+9
            if (o.opacityBorder === false) {
                boxMode.push('cp-box-no-opacity');
                border = 0;
            }
            // 无内边框
            o.chromeless && boxMode.push('cp-box-chromeless');
            // 自定义皮肤
            o.skin && boxMode.push(o.skin);
            // 遮罩颜色
            oBoxMask.style.backgroundColor = (typeof o.maskColor == 'string') ? o.maskColor : '#ccc';
            // 遮罩透明度
            o.opacity = parseFloat(o.opacity) || 0.7;
            oBoxMask.style.filter = 'alpha(opacity = ' + o.opacity * 100 + ')';
            oBoxMask.style.mozOpacity = oBoxMask.style.khtmlOpacity = oBoxMask.style.opacity = o.opacity;
            // 尺寸
            oBox.style.width = ((o.w ? parseInt(o.w, 10) : 476) + border) + 'px';
            // 内容
            if (!o.chromeless) {
                setTitle(o.title || '');
                Util.show('cp_box_title');
            } else {
                Util.hide('cp_box_title');
            }
            // closer
            o.closer ? Util.hide('cp_box_closer') : Util.show('cp_box_closer');
            // 弹层背景透明           
            $('cp_box_inner').style.backgroundColor = (o.bgTransparent !== true) ? '#fff' : 'transparent';
            switch (scene) {
                case 'text':
                    oBox.style.height = o.h ? (o.h + 'px') : 'auto';
                    setContent(o.info);
                    // icon
                    o.icon = parseInt(o.icon, 10) || 0;
                    if (o.icon < 1 || o.icon > 5) {
                        Util.hide('cp_box_icon');
                        $('cp_box_content_main').className = '';
                    } else {
                        boxMode.push('cp-box-std cp-box-tips'); // 提示类规范(缺省有关闭按钮)
                        if (!o.btns) o.btns = [['关闭', CPBox.close]];
                        Util.show('cp_box_icon');
                        var arrIcon = ['cp-box-ico-ok', 'cp-box-ico-err', 'cp-box-ico-warn', 'cp-box-ico-notice', 'cp-box-ico-qna'];
                        $('cp_box_icon').className = 'cp-box-ico ' + arrIcon[o.icon - 1];
                        $('cp_box_content_main').className = 'cp-box-content-main';
                    }
                    break;
                case 'frame':
                    oBox.style.height = 'auto';
                    Util.hide('cp_box_icon');
                    setContent(o);
                    break;
            }
            // btns
            if (typeof o.btns == 'object') {
                var btn = $('cp_box_ft').getElementsByTagName('input');
                if (btn.length < 1) {
                    btn = $('cp_box_ft').getElementsByTagName('a');
                }
                for (var i = 0; i < 2; i++) {
                    if (o.btns[i]) {
                        btn[i].value = o.btns[i][0];
                        btn[i].onclick = o.btns[i][1];
                        btn[i].parentNode.style.display = 'inline-block';
                    } else {
                        Util.hide(btn[i].parentNode);
                    }
                }
                Util.show('cp_box_ft');
            } else {
                Util.hide('cp_box_ft');
            }
            // 绑定closer事件同时为关闭按钮追加事件
            $('cp_box_closer').onclick = function() {
                CPBox.close();
                if (typeof o.cfn == 'function') {
                    o.cfn();
                }
                return false;
            };
            oBox.className = boxMode.join(' ');
        };
        /** 设置Box标题 */
        var setTitle = function(title) {
            $('cp_box_title_main').innerHTML = title || '';
        };
        /**
         * 在打开的Box中设置内容
         * @param {String/Object} o 为String则设置文本内容; 为Object则设置iframe内容
         *
         * @example
             1) setContent('box');
             2) setContent({
                    h: 100,
                    url: url,
                    monitor: true,
                    fn: function() {}
                });
         */
        var setContent = function(o) {
            if (typeof o != 'object') {
                $('cp_box_content_main').innerHTML = o || '';
                if (_isIE6) {
                   bugFix.sel();
                   bugFix.odd(); // bugFix: ie6基数bug
                }
            } else {
                // 防止ie出现'已释放Script的代码'错误,在iframe加载完成后再释放前一个iframe
                iframeTimer && clearInterval(iframeTimer);
                var oBoxFrame = $('cp_box_iframe');
                if (!oBoxFrame) {
                    $('cp_box_content_main').innerHTML = '<div id="cp_box_iframe_loading" class="cp-box-loading cp-box-loading-iframe">正在载入...</div><iframe id="cp_box_iframe" frameborder="0" class="cp-box-iframe" style="display:none;" src="' + _SECURE_PAGE + '"></iframe>';
                    oBoxFrame = $('cp_box_iframe');
                } else {
                    Util.show('cp_box_iframe_loading');
                }
                oBoxFrame.id = 'cp_box_iframe_tmp';
                Util.hide(oBoxFrame);
                Loader.loadFrame({
                    id: 'cp_box_iframe',
                    css: 'cp-box-iframe',
                    w: o.w2,
                    h: o.h,
                    scroll: o.scroll,
                    url: o.url,
                    fn: function() {
                        Util.hide('cp_box_iframe_loading').show('cp_box_iframe', true);
                        try { // 防止fn的引用地址不存在而出现'不能执行已释放Script的代码'错误
                            if (typeof o.fn == 'function') {
                                o.fn();
                            }
                        } catch (e) {}
                        // 局中或偏移标记位
                        $('cp_box_iframe').setAttribute('data-offset', (typeof o.offset == 'object') ? '1' : '0');
                        // 防止iframe页面渲染时间造成的ie Object required错误
                        setTimeout(function() {
                            resetFrameSize(o.h);
                        }, 0);
                        if (o.monitor && typeof o.h != 'number') {
                            frameMonitor();
                        }
                        var iframe = $('cp_box_iframe_tmp');
                        iframe && iframe.parentNode.removeChild(iframe);
                        if (_isIE6) {
                            bugFix.odd(); // bugFix: ie6基数bug
                        }
                    },
                    target: 'cp_box_content_main'
                });
            }
        };
        /** 偏移 */
        var offset = function(x, y) {
            var oBoxFrame = $('cp_box_iframe');
            if (oBoxFrame) {
                oBoxFrame.setAttribute('data-offset', '1');
            }
            if (x == 'auto' && y == 'auto') {
                center();
            } else if (x == 'auto' && y != 'auto') {
                var arrObjSize = Size.getObjSize(oBox);
                oBox.style.left = '50%';
                oBox.style.top = 0;
                oBox.style.marginLeft = -arrObjSize[0] / 2 + 'px';
                oBox.style.marginTop = y + 'px';
            } else if (x != 'auto' && y == 'auto') {
                var arrObjSize = Size.getObjSize(oBox);
                var arrScrollSize = Size.getScrollSize();
                oBox.style.left = 0;
                oBox.style.top = '50%';
                oBox.style.marginLeft = x + 'px';
                oBox.style.marginTop = -arrObjSize[1] / 2 + (_isIE6 ? arrScrollSize[1] : 0) + 'px';
            } else {
                oBox.style.left = 0;
                oBox.style.top = 0;
                oBox.style.marginLeft = x + 'px';
                oBox.style.marginTop = y + 'px';
            }
        };
        /** 局中 */
        var center = function() {
            var oBoxFrame = $('cp_box_iframe');
            if (oBoxFrame) {
                oBoxFrame.setAttribute('data-offset', '0');
            }
            var arrPageSize = Size.getPageSize();
            oBoxMask.style.width = arrPageSize[0] + 'px';
            oBoxMask.style.height = arrPageSize[1] + 'px';
            var arrObjSize = Size.getObjSize(oBox);
            var arrScrollSize = Size.getScrollSize();
            oBox.style.left = '50%';
            oBox.style.top = '50%';
            oBox.style.marginLeft = -arrObjSize[0] / 2 + 'px';
            oBox.style.marginTop = -arrObjSize[1] / 2 + (_isIE6 ? arrScrollSize[1] : 0) + 'px';
        };
        /** 重设iframe高度 */
        var resetFrameSize = function(h) {
            var oBoxFrame = $('cp_box_iframe');
            if (!oBoxFrame) {
                return;
            }
            if (typeof h == 'number') {
                oBoxFrame.style.height = h + 'px';
            } else {
                oBoxFrame.style.height = 'auto';
                try { // 防止跨域访问
                    var arrFrameSize = Size.getFrameSize(oBoxFrame);
                    oBoxFrame.style.height = arrFrameSize[1] + 'px';
                } catch (e) {}
            }
            if (_isIE6) {
                oBox.className = oBox.className; // bugFix: ie6渲染bug
            }
            // 局中或偏移,偏移时不作处理
            if (oBoxFrame.getAttribute('data-offset') === '0') {
                center();
            }
        };
        /** 监控iframe尺寸变化(定义了高度时无效) */
        var frameMonitor = function() {
            iframeTimer = setInterval(function() {
                resetFrameSize();
            }, 500);
        };
        /**
         * 显示
         * @param {Object} o 配置
         */
        var show = function(o) {
            isBoxOpen && close();// 销毁上次打开的Box
            Util.show([oBoxMask, oBox]);
            // 局中或偏移
            if (typeof o.offset == 'object') {
                offset(o.offset[0], o.offset[1]);
            } else {
                center();
            }
            Event.addEvent(window, 'resize', resize);
            // ie6 bug fix
            if (_isIE6 && o.fixed !== false) {
                Event.addEvent(window, 'scroll', bugFix.fixed);
            }
            isBoxOpen = true;
            return false;
        };
        /** mask reset */
        var resize = function() {
            var arrScrollSize = Size.getScrollSize();
            var arrWinSize = Size.getWinSize();
            oBoxMask.style.width = ((arrScrollSize[2] > 0) ? (arrWinSize[0] + arrScrollSize[2]) : arrWinSize[0]) + 'px';
            oBoxMask.style.height = ((arrScrollSize[3] > 0) ? (arrWinSize[1] + arrScrollSize[3]) : arrWinSize[1]) + 'px';
        };
        // bug fix
        var bugFix = {
            /**
             * 修复滚动时select闪烁问题
             * @param {Boolean} [undo:false] 是否撤销
             */
            sel: function(undo) {
                var oSel = document.getElementsByTagName('select');
                var fun = undo ? Util.show : Util.hide;
                for (var i = 0, len = oSel.length; i < len; i++) {
                    fun(oSel[i], true);
                }
            },
            // ie6 fixed问题
            fixed: function() {
                var arrObjSize = Size.getObjSize(oBox);
                var arrScrollSize = Size.getScrollSize();
                oBox.style.marginTop = -arrObjSize[1] / 2 + arrScrollSize[1] + 'px';
            },
            // ie6 基数定位bug
            odd: function() {
                var objInner = $('cp_box_inner');
                var objInnerSize = Size.getObjSize(objInner);
                if (objInnerSize[1] % 2 != 0) {
                    $('cp_box_bl').style.bottom = $('cp_box_br').style.bottom = '-2px';
                } else {
                    $('cp_box_bl').style.bottom = $('cp_box_br').style.bottom = '-1px';
                }
            }
        };

        return {
            /**
             * 获取当前Box的name
             * @return {String}
             */
            name: function() {
                return name;
            },
            /**
             * Box是否处于打开状态
             * @return {Boolean}
             */
            isOpen: function() {
                return isBoxOpen;
            },
            /**
             * 获取Box内部对象引用
             * @return {Object} {'box':obj1, 'boxFrame':obj2, 'boxCloser':obj3}
             */
            obj: function() {
                return {
                    'box': oBox,
                    'boxFrame': $('cp_box_iframe'),
                    'boxCloser': $('cp_box_closer')
                };
            },
            /**
             * 文本弹出层
             * @param {Object} o 配置
             * @example
             * Box.text({
             *     [w:476]: 300, // 宽度
             *     [h:auto]: 200, // 此处设置的是box的整体高度
             *     [offset:null]: [x, y], // XY轴偏移量,此时不局中; 为auto时局中偏移
             *     [name:CP_Box]: '', // 弹层名称
             *     [fixed:true]: false, // 是否固定
             *     [maskColor:#CCC]: '#FF6600', // 遮罩颜色
             *     [opacity:0.7]: 0.5, // 遮罩透明度
             *     [opacityBorder:true]: false, // 是否显示透明度边框
             *     [skin:'']: 'new', // 皮肤
             *     [chromeless:false]: true, //无边框
             *     [title:'']: '提示', // 标题
             *     info: '提示内容', // 内容
             *     [icon:false]: 1, // icon; 可选值: 1,2,3,4,5; 对应['ok', 'err', 'warn', 'notice', 'qna']
             *     [btns:false]: [['是', function(){}], ['否', function(){}]], // 自定义按钮事件
             *     [closer:false]: true, // 是否隐藏关闭按钮
             *     [cfn:null]: function() {} // 追加关闭按钮事件
             *     [bgTransparent:false]: true  //弹层背景透明
             * });
             */
            text: function(o) {
                init();
                setter(o, 'text');
                show(o);
            },
            /**
             * iframe弹出层
             * @param {Object} o 配置
             * @example
             * Box.frame({
             *     [w:476]: 300, // 宽度
             *     [w2:auto]: 200, // 此处设置的是iframe的宽度,缺省100%自适应
             *     [offset:null]: [x, y], // XY轴偏移量,此时不局中; 为auto时局中偏移
             *     [name:CP_Box]: '', // 弹层名称
             *     [h:auto]: 200, // 此处设置的是iframe的高度
             *     [fixed:true]: false, // 是否固定
             *     [maskColor：#CCC]: '#FF6600', // 遮罩颜色
             *     [opacity:0.7]: 0.5, // 遮罩透明度
             *     [opacityBorder:true]: false, // 透明度边框
             *     [skin:'']: 'new', // 皮肤
             *     [chromeless:false]: true, // 无边框
             *     [scroll:'']: 'no', // scrolling
             *     [monitor:false]: true, // 监控iframe高度变化
             *     [title:'']: '提示', // 标题
             *     url: url, // iframe地址
             *     [fn:null]: function() {}, // iframe加载完成回调
             *     [closer:false]: true, // 是否隐藏关闭按钮
             *     [cfn:null]: function() {} // 追加关闭按钮事件
             *     [bgTransparent:false]: true  //弹层背景透明
             * });
             */
            frame: function(o) {
                init();
                setter(o, 'frame');
                show(o);
            },
            /**
             * 偏移
             */
            offset: offset,
            /**
             * 局中
             */
            center: center,
            /**
             * 重设iframe尺寸
             */
            resetFrameSize: resetFrameSize,
            /**
             * 更改标题
             */
            setTitle: setTitle,
            /**
             * 设置Box内容
             * @param {String|Object} o 为String则设置文本内容; 为Object则设置iframe内容
             * @example
             * setContent('box');
             * setContent({
             *     url: url,
             *     monitor: true,
             *     fn: function() {}
             * });
             */
            setContent: setContent,
            /**
             * 关闭Box
             * @function
             */
            close: close
        };
    }();
    
    // expose
    window.CPBox = Box;
    
    // css预加载
    Loader.loadCss(_css);
    
})();
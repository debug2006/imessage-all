/**
 * �Ƹ�ͨ��Ʊ - Boxģ��
 * @author: zawa
 * @date: 2012/09/19
 * 
 * @modify:
 *      2012/09/19 zawa �Ӳ�ƱAPI�������
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
        _SECURE_PAGE = _isIE ? 'javascript:\'\'' : 'about:blank', // ��̬IFRAMEʱ�õİ�ȫҳ
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
             * ����¼�
             * @param {Object} el ����
             * @param {String} eventName �¼�����(����on)
             * @param {Function} fn ����
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
             * �Ƴ��¼�
             * @param {Object} el ����
             * @param {String} eventName �¼�����(����on)
             * @param {Function} fn ����
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
         * ��ʾ
         * @param {String|Array|Object} id
         * @param {Boolean} [visible:false] �Ƿ�Ϊvisibility
         * @return {Object} this ����ʽ����
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
         * ����
         * @param {String|Array|Object} id
         * @param {Boolean} [visible:false] �Ƿ�Ϊvisibility
         * @return {Object} this ����ʽ����
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
     * �ߴ�
     */
    var Size = {
        /**
         * ��ȡ����ߴ�
         * @param {String|Object} id
         * @return {Array} [width, height]
         */
        getObjSize: function(id) {
            var obj = $(id);
            return [obj.offsetWidth, obj.offsetHeight];
        },
        /**
         * ��ȡҳ��ʵ�ʳߴ�
         * @return {Array} [width, height]
         */
        getPageSize: function() {
            return [_html.scrollWidth, _html.scrollHeight];
        },
        /**
         * ��ȡ���ڿ��ӳߴ�
         * @return {Array} [width, height]
         */
        getWinSize: function() {
            var winX = document.documentElement.clientWidth || window.innerWidth,
                winY = document.documentElement.clientHeight || window.innerHeight;
            return [winX, winY];
        },
        /**
         * ��ȡ���ڹ����ߴ�
         * @return {Array} [ˮƽ����, ��ֱ����, ˮƽall, ��ֱall]
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
         * ��ȡiframe�ߴ�
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
                h = Math.max(iHeight1, iHeight2, iBodyHeight, iHtmlHeight) + 2; // MSIE 2px �ĵ��߿�ֵ(IE6�´���), ����MSIE��û��0,0���ĵ���ʼλ�ã����ͨ��������2px�ı߿�����Χ
            return [w, h];
        }
    };
    
    /**
     * Loader
     */
    var Loader = {
        /**
         * ������ʽ
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
         * ����������iframe(�������Ѵ�����ֱ�Ӽ���)
         * @param {Object} o ���ö���
         * {
         *     o.url: {String} iframe��ַ
         *     [o.id]: {String/Object} ��idָ��Ķ�������򲻴���
         *     [o.css]: {String} ��ʽ��
         *     [o.w]: {Int} iframe���
         *     [o.h]: {Int} iframe�߶�
         *     [o.scroll]: {String} scrolling
         *     [o.fn]: {Function} �ص�
         *     [o.target]: {String/Object} Ҫ����ĸ��ڵ�(����iframe��ָ����iframe������ʱ��ָ��)
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
            
        /** ��ʼ��Box */
        var init = function() {
            if (oBox) {
                return;
            }
            // box
            var str = [];
            str.push('<div id="cp_box_inner" class="cp-box">');
            str.push('<a id="cp_box_closer" class="cp-box-closer" href="###" title="�ر�"><!--closer--></a>');
            str.push('<div id="cp_box_title" class="cp-box-title"><span id="cp_box_title_main" class="cp-box-title-main"></span></div>');
            str.push('<div id="cp_box_content" class="cp-box-content"><span id="cp_box_icon" class="cp-box-ico"><!--ico--></span><div id="cp_box_content_main"></div></div>');
            str.push('<div id="cp_box_ft" class="cp-box-ft"><span class="cp-box-btn-bt"><input type="button" value="ȷ��" /></span><span class="cp-box-btn-bt"><input type="button" value="ȡ��" /></span></div>');
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
        /** �ر� */
        var close = function() {
            iframeTimer && clearInterval(iframeTimer);
            Util.hide([oBoxMask, oBox]);
            // ����onresize�¼�
            Event.removeEvent(window, 'resize', resize);
            if (_isIE) {
                bugFix.sel(true);
            }
            // ����ie6 bug fix�¼�
            if (_isIE6) {
                Event.removeEvent(window, 'scroll', bugFix.fixed);
            }
            isBoxOpen = false;
            return false;
        };
        /**
         * ����
         * @param {Object} o ����
         * @param {String} scene ʹ�ó���
         */
        var setter = function(o, scene) {
            name = o.name || 'CP_Box';
            // �����Զ�����ʽ,���ڹ淶
            var boxMode = ['cp-box-outer'];
            // �Ƿ�̶�(IE6��֧��fixed)
            oBox.style.position = (o.fixed === false || _isIE6) ? 'absolute' : 'fixed';
            // ͸������߿�
            var border = 20; // ͸���߿�+borderֵ:9+1+1+9
            if (o.opacityBorder === false) {
                boxMode.push('cp-box-no-opacity');
                border = 0;
            }
            // ���ڱ߿�
            o.chromeless && boxMode.push('cp-box-chromeless');
            // �Զ���Ƥ��
            o.skin && boxMode.push(o.skin);
            // ������ɫ
            oBoxMask.style.backgroundColor = (typeof o.maskColor == 'string') ? o.maskColor : '#ccc';
            // ����͸����
            o.opacity = parseFloat(o.opacity) || 0.7;
            oBoxMask.style.filter = 'alpha(opacity = ' + o.opacity * 100 + ')';
            oBoxMask.style.mozOpacity = oBoxMask.style.khtmlOpacity = oBoxMask.style.opacity = o.opacity;
            // �ߴ�
            oBox.style.width = ((o.w ? parseInt(o.w, 10) : 476) + border) + 'px';
            // ����
            if (!o.chromeless) {
                setTitle(o.title || '');
                Util.show('cp_box_title');
            } else {
                Util.hide('cp_box_title');
            }
            // closer
            o.closer ? Util.hide('cp_box_closer') : Util.show('cp_box_closer');
            // ���㱳��͸��           
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
                        boxMode.push('cp-box-std cp-box-tips'); // ��ʾ��淶(ȱʡ�йرհ�ť)
                        if (!o.btns) o.btns = [['�ر�', CPBox.close]];
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
            // ��closer�¼�ͬʱΪ�رհ�ť׷���¼�
            $('cp_box_closer').onclick = function() {
                CPBox.close();
                if (typeof o.cfn == 'function') {
                    o.cfn();
                }
                return false;
            };
            oBox.className = boxMode.join(' ');
        };
        /** ����Box���� */
        var setTitle = function(title) {
            $('cp_box_title_main').innerHTML = title || '';
        };
        /**
         * �ڴ򿪵�Box����������
         * @param {String/Object} o ΪString�������ı�����; ΪObject������iframe����
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
                   bugFix.odd(); // bugFix: ie6����bug
                }
            } else {
                // ��ֹie����'���ͷ�Script�Ĵ���'����,��iframe������ɺ����ͷ�ǰһ��iframe
                iframeTimer && clearInterval(iframeTimer);
                var oBoxFrame = $('cp_box_iframe');
                if (!oBoxFrame) {
                    $('cp_box_content_main').innerHTML = '<div id="cp_box_iframe_loading" class="cp-box-loading cp-box-loading-iframe">��������...</div><iframe id="cp_box_iframe" frameborder="0" class="cp-box-iframe" style="display:none;" src="' + _SECURE_PAGE + '"></iframe>';
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
                        try { // ��ֹfn�����õ�ַ�����ڶ�����'����ִ�����ͷ�Script�Ĵ���'����
                            if (typeof o.fn == 'function') {
                                o.fn();
                            }
                        } catch (e) {}
                        // ���л�ƫ�Ʊ��λ
                        $('cp_box_iframe').setAttribute('data-offset', (typeof o.offset == 'object') ? '1' : '0');
                        // ��ֹiframeҳ����Ⱦʱ����ɵ�ie Object required����
                        setTimeout(function() {
                            resetFrameSize(o.h);
                        }, 0);
                        if (o.monitor && typeof o.h != 'number') {
                            frameMonitor();
                        }
                        var iframe = $('cp_box_iframe_tmp');
                        iframe && iframe.parentNode.removeChild(iframe);
                        if (_isIE6) {
                            bugFix.odd(); // bugFix: ie6����bug
                        }
                    },
                    target: 'cp_box_content_main'
                });
            }
        };
        /** ƫ�� */
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
        /** ���� */
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
        /** ����iframe�߶� */
        var resetFrameSize = function(h) {
            var oBoxFrame = $('cp_box_iframe');
            if (!oBoxFrame) {
                return;
            }
            if (typeof h == 'number') {
                oBoxFrame.style.height = h + 'px';
            } else {
                oBoxFrame.style.height = 'auto';
                try { // ��ֹ�������
                    var arrFrameSize = Size.getFrameSize(oBoxFrame);
                    oBoxFrame.style.height = arrFrameSize[1] + 'px';
                } catch (e) {}
            }
            if (_isIE6) {
                oBox.className = oBox.className; // bugFix: ie6��Ⱦbug
            }
            // ���л�ƫ��,ƫ��ʱ��������
            if (oBoxFrame.getAttribute('data-offset') === '0') {
                center();
            }
        };
        /** ���iframe�ߴ�仯(�����˸߶�ʱ��Ч) */
        var frameMonitor = function() {
            iframeTimer = setInterval(function() {
                resetFrameSize();
            }, 500);
        };
        /**
         * ��ʾ
         * @param {Object} o ����
         */
        var show = function(o) {
            isBoxOpen && close();// �����ϴδ򿪵�Box
            Util.show([oBoxMask, oBox]);
            // ���л�ƫ��
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
             * �޸�����ʱselect��˸����
             * @param {Boolean} [undo:false] �Ƿ���
             */
            sel: function(undo) {
                var oSel = document.getElementsByTagName('select');
                var fun = undo ? Util.show : Util.hide;
                for (var i = 0, len = oSel.length; i < len; i++) {
                    fun(oSel[i], true);
                }
            },
            // ie6 fixed����
            fixed: function() {
                var arrObjSize = Size.getObjSize(oBox);
                var arrScrollSize = Size.getScrollSize();
                oBox.style.marginTop = -arrObjSize[1] / 2 + arrScrollSize[1] + 'px';
            },
            // ie6 ������λbug
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
             * ��ȡ��ǰBox��name
             * @return {String}
             */
            name: function() {
                return name;
            },
            /**
             * Box�Ƿ��ڴ�״̬
             * @return {Boolean}
             */
            isOpen: function() {
                return isBoxOpen;
            },
            /**
             * ��ȡBox�ڲ���������
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
             * �ı�������
             * @param {Object} o ����
             * @example
             * Box.text({
             *     [w:476]: 300, // ���
             *     [h:auto]: 200, // �˴����õ���box������߶�
             *     [offset:null]: [x, y], // XY��ƫ����,��ʱ������; Ϊautoʱ����ƫ��
             *     [name:CP_Box]: '', // ��������
             *     [fixed:true]: false, // �Ƿ�̶�
             *     [maskColor:#CCC]: '#FF6600', // ������ɫ
             *     [opacity:0.7]: 0.5, // ����͸����
             *     [opacityBorder:true]: false, // �Ƿ���ʾ͸���ȱ߿�
             *     [skin:'']: 'new', // Ƥ��
             *     [chromeless:false]: true, //�ޱ߿�
             *     [title:'']: '��ʾ', // ����
             *     info: '��ʾ����', // ����
             *     [icon:false]: 1, // icon; ��ѡֵ: 1,2,3,4,5; ��Ӧ['ok', 'err', 'warn', 'notice', 'qna']
             *     [btns:false]: [['��', function(){}], ['��', function(){}]], // �Զ��尴ť�¼�
             *     [closer:false]: true, // �Ƿ����عرհ�ť
             *     [cfn:null]: function() {} // ׷�ӹرհ�ť�¼�
             *     [bgTransparent:false]: true  //���㱳��͸��
             * });
             */
            text: function(o) {
                init();
                setter(o, 'text');
                show(o);
            },
            /**
             * iframe������
             * @param {Object} o ����
             * @example
             * Box.frame({
             *     [w:476]: 300, // ���
             *     [w2:auto]: 200, // �˴����õ���iframe�Ŀ��,ȱʡ100%����Ӧ
             *     [offset:null]: [x, y], // XY��ƫ����,��ʱ������; Ϊautoʱ����ƫ��
             *     [name:CP_Box]: '', // ��������
             *     [h:auto]: 200, // �˴����õ���iframe�ĸ߶�
             *     [fixed:true]: false, // �Ƿ�̶�
             *     [maskColor��#CCC]: '#FF6600', // ������ɫ
             *     [opacity:0.7]: 0.5, // ����͸����
             *     [opacityBorder:true]: false, // ͸���ȱ߿�
             *     [skin:'']: 'new', // Ƥ��
             *     [chromeless:false]: true, // �ޱ߿�
             *     [scroll:'']: 'no', // scrolling
             *     [monitor:false]: true, // ���iframe�߶ȱ仯
             *     [title:'']: '��ʾ', // ����
             *     url: url, // iframe��ַ
             *     [fn:null]: function() {}, // iframe������ɻص�
             *     [closer:false]: true, // �Ƿ����عرհ�ť
             *     [cfn:null]: function() {} // ׷�ӹرհ�ť�¼�
             *     [bgTransparent:false]: true  //���㱳��͸��
             * });
             */
            frame: function(o) {
                init();
                setter(o, 'frame');
                show(o);
            },
            /**
             * ƫ��
             */
            offset: offset,
            /**
             * ����
             */
            center: center,
            /**
             * ����iframe�ߴ�
             */
            resetFrameSize: resetFrameSize,
            /**
             * ���ı���
             */
            setTitle: setTitle,
            /**
             * ����Box����
             * @param {String|Object} o ΪString�������ı�����; ΪObject������iframe����
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
             * �ر�Box
             * @function
             */
            close: close
        };
    }();
    
    // expose
    window.CPBox = Box;
    
    // cssԤ����
    Loader.loadCss(_css);
    
})();
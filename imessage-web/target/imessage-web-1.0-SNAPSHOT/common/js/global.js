
/**
 * @namespace 彩种相关
 */
var CPCategory = function() {
	// 彩种配置

        // 'ssq': 红球;篮球|投注方式|倍数-红球;篮球|投注方式|倍数
        // '3d' : 号码|玩法|投注方式|倍数-号码|玩法|投注方式|倍数
        // 'qlc': 号码|玩法|投注方式|倍数-号码|玩法|投注方式|倍数
        // 'ssc': 号码|玩法|投注方式|倍数-号码|玩法|投注方式|倍数
	// 'dlt': 号码|投注方式|倍数|追加-号码|投注方式|倍数|追加
	// '115': 号码|投注代码|倍数-号码|投注代码|倍数
        // 'qxc': 号码|投注方式|倍数-号码|投注方式|倍数

        // 【投注格式format标识说明】: 若需转义,以对象形式定义即可
        // code: 号码
        // betWay: 投注方式文本
        // betWayFlag: 投注方式标识
        // play: 玩法文本
        // playFlag: 玩法标识
        // multi: 倍数
        // addto: 追加
	var category = {
		'ssq': {
			name: '双色球',
			format: 'code|betWay|multi', // 投注格式
			betWay: { // 投注方式映射
				'1': '单式',
				'2': '复式',
				'3': '胆拖'
			}
		},
		'3d': {
			name: '福彩3D',
			format: 'code|play|betWay|multi',
			play: {
				'1': '直选',
				'2': '组三',
				'3': '组六'
			},
			betWay: {
				'1': '单式',
				'2': '复式',
				'3': '包号',
				'4': '和值'
			}
		},
		'qlc': {
			name: ' 七乐彩',
			format: 'code|play|betWay|multi',
			play: {
				 '1': '' // 玩法固定为1
			},
			betWay: {
				'1': '单式',
				'2': '复式',
				'3': '胆拖'
			}
		},
        '155': {
            name: '15选5',
            format: 'code|play|betWay|multi',
            play: {
                '1': '' // 玩法固定为1
            },
            betWay: {
                '1': '单式',
                '2': '复式',
                '5': '胆拖'
            }
        },
		'ssc': {
			name: '新时时彩',
			format: 'code|play|betWay|multi',
			play: {
				'1': '一星直选',
				'2': '二星直选',
				'3': '三星直选',
				'4': '四星直选',
				'5': '五星直选',
				'6': '二星复选',
				'7': '三星复选',
				'8': '四星复选',
				'9': '五星复选',
				'10': '二星组选',
				'11': '大小单双',
				'12': '五星通选',
				'13': '任选一',
				'14': '任选二',
				'15': '三星组三',
				'16': '三星组六'
			},
			betWay: {
				'1': '单式',
				'2': '复式',
				'3': '包号',
				'4': '和值',
				'5': '胆拖'
			}
		},
		'k3k': {
			name: '快3',
			format: 'code|play|betWay|multi',
			play: {
				'1': '和值',
				'2': '三同号通选',
				'3': '三同号单选',
				'4': '三不同号',
				'5': '三连号',
				'6': '二同号复选',
				'7': '二同号单选',
				'8': '二不同号'
			},
			betWay: {
				'1': '单式',
				'2': '复式',
				'4': '和值',
				'5': '胆拖'
			}
		},
        'csc': {
			name: '老时时彩',
			format: 'code|play|betWay|multi',
			play: {
				'1': '一星直选',
				'2': '二星直选',
				'3': '三星直选',
				'4': '四星直选',
				'5': '五星直选',
				'6': '二星复选',
				'7': '三星复选',
				'8': '四星复选',
				'9': '五星复选',
				'10': '二星组选',
				'11': '大小单双',
				'12': '五星通选',
				'13': '任选一',
				'14': '任选二',
				'15': '三星组三',
				'16': '三星组六'
			},
			betWay: {
				'1': '单式',
				'2': '复式',
				'3': '包号',
				'4': '和值',
				'5': '胆拖'
			}
		},
		'dlt': {
			name: '大乐透',
			format: 'code|betWay|multi|addto',
			betWay: {
				'1': '单式',
				'2': '复式',
				'3': '胆拖',
				'4': '生肖乐单式',
				'5': '生肖乐复式'
			}
		},
		'115': {
			name: '十一运夺金',
			format: 'code|betWay|multi',
			betWay: {
				'101': '任选一复式',
				'102': '任选二复式',
				'103': '任选三复式',
				'104': '任选四复式',
				'105': '任选五复式',
				'106': '任选六复式',
				'107': '任选七复式',
				'108': '选前二组选复式',
				'109': '选前三组选复式',
				'111': '任选二单式',
				'112': '任选三单式',
				'113': '任选四单式',
				'114': '任选五单式',
				'115': '任选六单式',
				'116': '任选七单式',
				'117': '任选八单式',
				'121': '任选二胆拖',
				'122': '任选三胆拖',
				'123': '任选四胆拖',
				'124': '任选五胆拖',
				'125': '任选六胆拖',
				'126': '任选七胆拖',
				'131': '选前二组选单式',
				'132': '选前二组选和值',
				'133': '选前二组选胆拖',
				'134': '选前二组选跨度',
				'141': '选前二直选单式',
				'142': '选前二直选定位复式',
				'143': '选前二直选和值',
				'144': '选前二直选复式',
				'145': '选前二直选跨度',
				'151': '选前三组选单式',
				'152': '选前三组选和值',
				'153': '选前三组选胆拖',
				'154': '选前三组选跨度',
				'161': '选前三直选单式',
				'162': '选前三直选定位复式',
				'163': '选前三直选和值',
				'164': '选前三直选复式',
				'165': '选前三直选跨度'
			}
		},
		'qxc': {
			name: '七星彩',
			format: 'code|betWay|multi',
			betWay: {
				'1': '单式',
				'2': '复式'
			}
		},
        'pl3': {
            name: '排列三',
            format: 'code|betWay|multi',
            betWay: {
                '1': '直选单式',
                '2': '直选复式',
                '3': '直选和值',
                '4': '组三单式',
                '5': '组三复式',
                '6': '组三和值',
                '7': '组六单式',
                '8': '组六复式',
                '9': '组六和值'
            }
        },
        'pl5': {
            name: '排列五',
            format: 'code|betWay|multi',
            betWay: {
                '1': '单式',
                '2': '复式'
            }
        },
		'225': {
			name: '22选5',
			format: 'code|betWay|multi',
			betWay: {
				'1': '单式',
				'2': '复式'
			}
		},
		'sfc': {
			name: '胜负彩',
			format: 'code|betWay|multi',
			betWay: {
				'1': '单式',
				'2': '复式'
			}
		},
		'rjc': {
			name: '任选九',
			format: 'code|betWay|multi',
			betWay: {
				'1': '单式',
				'2': '复式'
			}
		},
        'h15': {
            name: '11选5(黑龙江)',
            format: 'code|betWay|multi',
            betWay: {
                '101': '任选一单式',
                '102': '任选二复式',
                '103': '任选三复式',
                '104': '任选四复式',
                '105': '任选五复式',
                '106': '任选六复式',
                '107': '任选七复式',
                '108': '选前二组选复式',
                '109': '选前三组选复式',
                '110': '任选一单式',
                '111': '任选二单式',
                '112': '任选三单式',
                '113': '任选四单式',
                '114': '任选五单式',
                '115': '任选六单式',
                '116': '任选七单式',
                '117': '任选八单式',
                '121': '任选二胆拖',
                '122': '任选三胆拖',
                '123': '任选四胆拖',
                '124': '任选五胆拖',
                '125': '任选六胆拖',
                '126': '任选七胆拖',
                '131': '选前二组选单式',
                '132': '选前二组选和值',
                '133': '选前二组选胆拖',
                '134': '选前二组选跨度',
                '141': '选前二直选单式',
                '142': '选前二直选定位复式',
                '143': '选前二直选和值',
                '144': '选前二直选复式',
                '145': '选前二直选跨度',
                '151': '选前三组选单式',
                '152': '选前三组选和值',
                '153': '选前三组选胆拖',
                '154': '选前三组选跨度',
                '161': '选前三直选单式',
                '162': '选前三直选定位复式',
                '163': '选前三直选和值',
                '164': '选前三直选复式',
                '165': '选前三直选跨度'
            }
        },
        'xync': {
            name: '快乐十分(重庆)',
            format: 'code|play|betWay|multi',
            play: {
                '1': '选一数投',
                '2': '选一红投',
                '3': '选二连组',
                '4': '选二连直',
                '5': '任选二',
                '6': '任选三',
                '7': '任选四',
                '8': '任选五',
                '9': '选三前直',
                '10': '选三前组'
            },
            betWay: {
                '1': '单式',
                '2': '复式'
            }
        },
        'zc4cj':{
            name: '4场进球',
            format: 'code|betWay|multi',
            betWay: {
                '1': '单式',
                '2': '复式'
            }
        },
        'zc6cb':{
            name: '6场半全',
            format: 'code|betWay|multi',
            betWay: {
                '1': '单式',
                '2': '复式'
            }
        },
		// 竞彩: 日期|周数|场次|注码|胆码-日期|周数|场次|注码|胆码$倍数$过关方式
        'jcbsk01': {
            name: '竞彩篮球胜负',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcbsk11': {
            name: '竞彩篮球胜负',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcbsk02': {
            name: '竞彩篮球让分胜负',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcbsk12': {
            name: '竞彩篮球让分胜负',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcbsk03': {
            name: '竞彩篮球胜分差',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcbsk13': {
            name: '竞彩篮球胜分差',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcbsk04': {
            name: '竞彩篮球大小分',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcbsk14': {
            name: '竞彩篮球大小分',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcft01': {
            name: '竞彩足球让球胜平负',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcft11': {
            name: '竞彩足球让球胜平负',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcft02': {
            name: '竞彩足球比分',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcft12': {
            name: '竞彩足球比分',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcft03': {
            name: '竞彩足球总进球',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcft13': {
            name: '竞彩足球总进球',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcft04': {
            name: '竞彩足球半全场',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },
        'jcft14': {
            name: '竞彩足球半全场',
            format: 'gameDay|weekID|teamID|code|codeD$multi$passType'
        },

        // 菠菜彩种(只需显示名字,不需作格式解释处理)
        'bc_gdx': {
            name: '新11选5(菠菜)',
            format: ''
        },
        'bc_dlc': {
            name: '11选5(菠菜)',
            format: ''
        },
        'bc_syy': {
            name: '老11选5(菠菜)',
            format: ''
        },
        'bc_jczq': {
            name: '竞彩足球(菠菜)',
            format: ''
        }
	};
        /**
	 * 投注格式解释器
	 * @param {String} type 彩种标识
	 * @param {String} code 投注号码格式串
	 * @return {Object}
	 */
	var parse = function(type, code) {
        type = type.toLowerCase();
            var curConf = category[type];
            if (!curConf) throw new Error('不存在此彩种的配置信息');
            var result = {};
            switch (type) {
                // 竞彩的格式与其它不一致
                case 'jcbsk01':
                case 'jcbsk11':
                case 'jcbsk02':
                case 'jcbsk12':
                case 'jcbsk03':
                case 'jcbsk13':
                case 'jcbsk04':
                case 'jcbsk14':
                case 'jcft01':
                case 'jcft11':
                case 'jcft02':
                case 'jcft12':
                case 'jcft03':
                case 'jcft13':
                case 'jcft04':
                case 'jcft14':
                    var format = curConf.format.split('$').join('|').split('|'),
                        code = code.split('$'),
                        arrCode = code[0].split('-'),
                        multi = code[1],
                        passType = code[2].split('|').join(','); // 过关方式以逗号分割
                    
                    // 为每注附加公共的"$倍数$过关方式"
                    for (var i = 0, len = arrCode.length; i < len; i++) {
                        arrCode[i] += '|' + multi + '|' + passType;
                    }
                    break;
                default:
                    var format = curConf.format.split('|'),
                        arrCode = code.split('-');
                    break;
            }
            for (var i = 0, len = arrCode.length; i < len; i++) {
                arrCode[i] = arrCode[i].split('|');
                for (var j = 0, len2 = arrCode[i].length; j < len2; j++) {
                    var flag = format[j];
                    if (!result[flag]) result[flag] = [];
                    if (typeof curConf[flag] == 'object') { // 配置里有转义的
                        result[flag].push(curConf[flag][arrCode[i][j]]);
                        // 有转义的都自动添加标识, eg: betWay -> betWayFlag
                        if (!result[flag + 'Flag']) result[flag + 'Flag'] = [];
                        result[flag + 'Flag'].push(arrCode[i][j]);
                    } else {
                        result[flag].push(arrCode[i][j]);
                    }
                }
            }
            return result;
	};

		/**
		* 获取代购单状态
		* @memberOf CP.Category
		* @param {Int} status 投注状态
		* @param {Int} isWin 中奖状态
		* @param {Int} back_award_status 返奖状态
		* @return {String}
		*/
		var getBetState_DG = function(status, is_win, back_award_status) {
			if (status == 3) { // 出票成功
				if(is_win == 2 || is_win == 3) {
					is_win = 2;
					if (back_award_status == 2) {
						back_award_status = 1;
					} else {
						back_award_status = 0;
					}
				} else {
					back_award_status = '';
				}
			} else {
				is_win = '';
				back_award_status = '';
			}
			var betState = {
				'0': '未支付',
				'1': '支付成功出票中',
				'2': '支付成功出票中',
				'30': '购买成功等待开奖',
				'31': '未中奖',
				'320': '已中奖派奖中',
				'321': '已派奖',
				'4': '购买失败退款中',
				'5': '购买失败退款中',
				'6': '购买失败退款中',
				'7': '购买失败已退款'
			};
			return betState[status + '' + is_win + '' + back_award_status];
		};
		/**
		 * 获取追号单状态
		 * @memberOf CP.Category
		 * @param {Int} status
		 * @return {String}
		 */
		var getBetState_ZH = function(status) {
			return (status == 1) ? '进行中' : '已完成';
		};
		/**
		 * 获取发起者或参与者合买单(投注)状态
		 * @memberOf CP.Category
		 * @param {Int} status 合买状态
		 * @param {Int} status2 投注状态
		 * @param {Int} isWin 中奖状态
		 * @param {Int} back_award_status 返奖状态
		 * @param {Int} type 1:参与者; other:发起者(缺省);
		 * @return {String}
		 */
		var getBetState_HM = function(status, status2, is_win, back_award_status, type) {
			type = (type == 1) ? 1 : 2;
			// 参与者时若投注状态的3个值(status2, is_win, back_award_status)存在即认为合买到达投注状态
			// 发起者时若'status=投注完成'即表示到达投注状态
			if ((type == 1 && !isNaN(status2) && !isNaN(is_win) && !isNaN(back_award_status)) || (type == 2 && status == 4)) {
				return getBetState_DG(status2, is_win, back_award_status);
			}
			var opt = (type == 1) ? ['未支付', '已支付', '加入合买中', '加入合买成功', '加入合买失败', '申请撤销合买', '合单撤销成功', '申请退款', '退款成功', '退款失败', '过期撤单', '作废'] : ['未支付', '进行中', '已可投注', '正在投注', '投注完成', '申请退款', '退款成功', '退款失败', '过期撤单', '作废', '用户撤单'];
			return opt[status * 1] || '未知';
		};

		return {
            parse: parse,
            /**
             * 拆分投注格式,并获取相关投注格式内容
             * 注意：返回值可能经过特殊处理,要获取原值请使用parse函数
             * @param {String} type 彩种类型
             * @param {String} code 投注号码
             * @param {String} [name] 要获取的投注格式内容名称,见彩种配置之【投注格式format标识说明】; 缺省时全部返回
             * @return {*}
             */
            get: function(type, code, name) {
                code = code.toLowerCase();
            	var result = parse(type, code);
                // 特殊处理
                switch (type) {
                    case '3d':
                        // 3D的包号都转换为复式显示
                        var curConf = category[type];
                        for (var i = 0, len = result['betWayFlag'].length; i < len; i++) {
                            if (result['betWayFlag'][i] == 3) {
                                result['betWayFlag'][i] = 2;
                                result['betWay'][i] = curConf['betWay'][2];
                            }
                        }
                        break;
                    case 'ssc':
                        // 时时彩'大小单双'不分'单式/复式'
                        for (var i = 0, len = result['playFlag'].length; i < len; i++) {
                            if (result['playFlag'][i] == 11) {
                                result['betWay'][i] = '';
                            }
                        }
                        break;
                }
                return (typeof name == 'undefined' ? result : result[name]) || '';
            },
            /**
             * 获取配置信息(取不到时返回空)
             * @param {String} [type] 彩种类型
             * @param {String} [name] 配置名称,缺省时返回整个配置信息
             * @return {*}
             */
            getConf: function(type, name) {
                if (typeof type == 'undefined') {
                    return category;
                } else {
                    type = type.toLowerCase();
                    return (typeof name != 'undefined' ? category[type] && category[type][name] : category[type]) || '';
                }
            },
			getBetState_DG: getBetState_DG,
			getBetState_ZH: getBetState_ZH,
			getBetState_HM: getBetState_HM,
            /**
    		 * 根据投注方式获取投注单状态
    		 * @param {String} betWay 投注方式 可选值:dg|zh|hm
    		 * @param {Array} opt 相应的状态码配置
    		 * @return {String}
    		 */
    		getBetState: function(betWay, opt) {
    			var state = '';
    			switch (betWay) {
    				case 'dg': // 代购
    					state = getBetState_DG.apply(null, opt);
    					break;
    				case 'zh': // 追号
    					state = getBetState_ZH.apply(null, opt);
    					break;
    				case 'hm': // 合买
    					state = getBetState_HM.apply(null, opt);
    					break;
    			}
    			return state;
    		},
            /**
    		 * 获取追号单状态(详情页)
    		 * @param {Int} trackStatus 追号单状态
    		 * @param {Int} trackRefundStatus 追号单撤单状态
    		 * @param {Int} status 投注单状态
    		 * @param {Int} is_win 投注单中奖状态
    		 * @param {Int} back_award_status 投注单返奖状态
    		 * @return {String}
    		 */
    		getTrackState: function(trackStatus, trackRefundStatus, status, is_win, back_award_status) {
        		//出票中：已经开始出票，尚未收到成功信息；
        		//已出票：出票后，开奖前显示该状态；
        		//未中奖：该期购买成功，且已开奖未中奖时，显示该状态；
        		//已中奖派奖中：该期购买成功，且已开奖中奖并尚未派奖成功时，显示该状态；
        		//已派奖：该期购买成功，且已开奖中奖并已派奖成功时，显示该状态；
        		//未开始：出票前显示该状态；
        		//退款中：出票不成功或用户手动取消时，显示该状态
        		//已退款：确认退款时显示该状态；

    			var trackState = '';
    			if (trackStatus == 2 || trackStatus == 4 || trackStatus == 5 || trackStatus == 6) { // 5是作废
    				trackState = (trackRefundStatus == 2) ? '已退款' : '退款中';
    			} else {
    				if (trackStatus == 3) { // 到达投注状态
    					if (status == 3) { // 出票成功
    						if (is_win == 0) { // 未开奖
    							trackState = '已出票';
    						} else {
    							if(is_win == 2 || is_win == 3) {
    								if (back_award_status == 2) {
    									trackState = '已派奖';
    								} else {
    									trackState = '已中奖派奖中';
    								}
    							} else {
    								trackState = '未中奖';
    							}
    						}
    					} else if (status == 7) {
    						trackState = '已退款';
    					} else if (status == 4 || status == 5 || status == 6) {
    						trackState = '退款中';
    					} else {
    						trackState = '出票中';
    					}
    				} else {
    					trackState = '未开始';
    				}
    			}
    			return trackState;
    		}
		};
	}();



/**
 * 彩种下拉
 * @param {String} wrapperID 容器ID,控件要插入的位置
 * @param {String} [val] 初始化选中的值(可选)
 * @param {String} [name:type] 下拉框的name,缺省为type(可选)
 */
function initCategorySel(wrapperID, val, name) {
	var str = [];
	val = (val || '').toLowerCase();
	name = name || 'type';
	str.push('<select id="' + name + '" name="' + name + '">');
	str.push('<option value="">所有</option>');
	var conf = CPCategory.getConf();
	var selected = '';
	for (var k in conf) {
		if (conf.hasOwnProperty(k)) {
			selected = (k.toLowerCase() == val) ? ' selected="selected"' : '';
			str.push('<option value="' + k.replace('3d', '3D') + '"' + selected + '>' + conf[k].name + '</option>');
		}
	}
	str.push('</select>');
	str = str.join('');
	document.getElementById(wrapperID).innerHTML = str;
}


/**
 * 彩种下拉2
 * @param {String} wrapperID 容器ID,控件要插入的位置
 * @param {String} [val] 初始化选中的值(可选)
 * @param {String} [name:type] 下拉框的name,缺省为type(可选)
 * @param {String} [isAll] 是否显示所有,缺省不显示; 其他时显示,同时设置all的value值为isAll
 */
function initCategorySel2(wrapperID, val, name, isAll) {
	var str = [];
	val = (val || '').toLowerCase();
	name = name || 'type';
	str.push('<select id="' + name + '" name="' + name + '">');
	if (typeof isAll != 'undefined') {
		str.push('<option value="' + isAll + '">所有</option>');
	}
	var conf = CPCategory.getConf();
	var selected = '';
	for (var k in conf) {
		if (conf.hasOwnProperty(k)) {
			selected = (k.toLowerCase() == val) ? ' selected="selected"' : '';
			str.push('<option value="' + k.replace('3d', '3D') + '"' + selected + '>' + conf[k].name + '</option>');
		}
	}
	str.push('</select>');
	str = str.join('');
	document.getElementById(wrapperID).innerHTML = str;
}

/**
 * 当前彩种
 * @param {String} wrapperID 容器ID,控件要插入的位置
 * @param {String} [type:type] 当前彩种名称type,缺省为type
 */
function initCategoryName(wrapperID, type) {
	var str = type ? (CPCategory.getConf(type, 'name') || type) : '所有';
	document.getElementById(wrapperID).innerHTML = str;
}

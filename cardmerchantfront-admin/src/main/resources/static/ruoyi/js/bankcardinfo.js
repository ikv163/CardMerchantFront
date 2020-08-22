(function() {
    var root = this;
    var cardTypeMap = {
        DC: "储蓄卡",
        CC: "信用卡",
        SCC: "准贷记卡",
        PC: "预付费卡"
    };

    function isFunction(fn) {
        return Object.prototype.toString.call(fn) === '[object Function]';
    }

    function extend(target, source) {
        var result = {};
        var key;
        target = target || {};
        source = source || {};
        for (key in target) {
            if (target.hasOwnProperty(key)) {
                result[key] = target[key];
            }
        }
        for (key in source) {
            if (source.hasOwnProperty(key)) {
                result[key] = source[key];
            }
        }
        return result;
    }

    function getCardTypeName(cardType) {
        if (cardTypeMap[cardType]) {
            return cardTypeMap[cardType]
        }
        return undefined;
    }


    function getBankNameByBankCode(bankcode) {
        for (var i = 0, len = bankcardList.length; i < len; i++) {
            var bankcard = bankcardList[i];
            if (bankcode == bankcard.bankCode) {
                return bankcard.bankName;
            }
        }
        return "";
    }

    function _getBankInfoByCardNo(cardNo, cbf) {
        for (var i = 0, len = bankcardList.length; i < len; i++) {
            var bankcard = bankcardList[i];
            var patterns = bankcard.patterns;
            for (var j = 0, jLen = patterns.length; j < jLen; j++) {
                var pattern = patterns[j];
                if ((new RegExp(pattern.reg)).test(cardNo)) {
                    var info = extend(bankcard, pattern);
                    delete info.patterns;
                    delete info.reg;
                    info['cardTypeName'] = getCardTypeName(info['cardType']);
                    return cbf(null, info);
                }
            }
        }
        return cbf(null);
    }

    function _getBankInfoByCardNoAsync(cardNo, cbf) {
        var errMsg = "";
        _getBankInfoByCardNo(cardNo, function(err, info) {
            if (!err && info) {
                return cbf(null, info);
            } else {
                if (typeof module !== 'undefined' && module.exports) {
                    var https = require('https');
                    https.get("https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=" + cardNo + "&cardBinCheck=true", function(res) {
                        if (res.statusCode == 200) {
                            var chunk = "";
                            res.on('data', function(d) {
                                chunk += d;
                            });
                            res.on('end', function() {
                                try {
                                    var bankInfo = JSON.parse(chunk);
                                    if (bankInfo.validated) {
                                        var info = {};
                                        info['bankName'] = getBankNameByBankCode(bankInfo.bank);
                                        info['cardType'] = bankInfo.cardType;
                                        info['bankCode'] = bankInfo.bank;
                                        info['cardTypeName'] = getCardTypeName(bankInfo.cardType);
                                        info['backName'] = info['bankName']; //向下兼容，修改字段错别字
                                        cbf(null, info);
                                    } else {
                                        errMsg = cardNo + ":该银行卡不存在," + chunk;
                                        cbf(errMsg);
                                    }
                                } catch (e) {
                                    errMsg = cardNo + ':获取alipay接口信息出错了,返回json格式不正确';
                                    cbf(errMsg);
                                }
                            })
                        } else {
                            errMsg = cardNo + ':获取alipay接口信息出错了,statusCode,' + res.statusCode;
                            cbf(errMsg);
                        }
                    }).on('error', function(e) {
                        errMsg = cardNo + ':获取alipay接口信息出错了';
                        cbf(errMsg);
                    });
                } else {
                    cbf(cardNo + ":该银行卡不存在");
                }
            }
        });
    }

    function getBankBin(cardNo, cbf) {
        var errMsg = '';
        if (!isFunction(cbf)) {
            cbf = function() {};
        }
        if (isNaN(cardNo)) {
            cardNo = parseInt(cardNo);
            if (isNaN(cardNo)) {
                errMsg = cardNo + ':银行卡号必须是数字';
                return cbf(errMsg)
            }
        }
        if (cardNo.toString().length < 15 || cardNo.toString().length > 19) {
            errMsg = cardNo + ':银行卡位数必须是15到19位';
            return cbf(errMsg)
        }
        _getBankInfoByCardNoAsync(cardNo, function(err, bin) {
            cbf(err, bin);
        });
    }

    function promiseApi(cardNo, cbf) {
        var Promise = require('bluebird');
        return new Promise(function(resolve, reject) {
            getBankBin(cardNo, function(err, data) {
                if (err) {
                    return reject(err)
                }
                resolve(data)
            })
        }).asCallback(cbf)
    }
    if (typeof exports !== 'undefined') {
        if (typeof module !== 'undefined' && module.exports) {
            exports = module.exports = {
                getBankBin: promiseApi
            };
        }
        exports.getBankBin = promiseApi;
    } else if (typeof define === 'function' && define.amd) {
        define('bankInfo', [], function() {
            return {
                getBankBin: getBankBin
            };
        });
    } else if (typeof define === 'function' && define.cmd) {
        define(function() {
            return {
                getBankBin: getBankBin
            };
        })
    } else {
        root.getBankBin = getBankBin;
    }
}.call(this));
package com.example.test.atmdemo.atmControl.serialPort;

/**
 * Created by lijie on 2017/5/4.
 */

public class CommandUtil {
    /*
     *出货命令
     * MDB刷卡支付出货   |支付金额|货道号|支付方式|卡号|
     */
    public static void shipWithMDBCardPay(String cardNumber, String selectedPrice, String selectedSlotNo) {
        if (null == selectedPrice || null == selectedSlotNo || null == cardNumber) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("|A|");
        sb.append(selectedPrice);
        sb.append("|"+selectedSlotNo);
        sb.append("|2|");
        sb.append(cardNumber);
        sb.append("|");
        String content="$$$"+sb.toString()+ Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    *出货命令
    * 刷中吉IC卡支付出货  |支付金额|货道号|支付方式|卡号|
    */
    public static void shipWithTcnCard(String cardNumber, String selectedPrice, String selectedSlotNo) {
        if (null == selectedPrice || null == selectedSlotNo || null == cardNumber) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("|A|");
        sb.append(selectedPrice);
        sb.append("|"+selectedSlotNo);
        sb.append("|4|");
        sb.append(cardNumber);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    *出货命令
    * 刷中吉IC卡支付出货  |支付金额|货道号|支付方式|卡号|
    */
    public static void shipWithTcnCard(int slotNo, String price, String cardNumber, boolean isVersionMoreThan22) {
        //解决有可能不出货的问题
        if (isVersionMoreThan22) {
            reqSelectGoodsBySlotNo(slotNo);
        } else {
            reqSelectGoods(slotNo);
        }
        StringBuffer sb = new StringBuffer();
        sb.append("|A|");
        sb.append(price);
        sb.append("|"+slotNo);
        sb.append("|4|");
        sb.append(cardNumber);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
     *出货命令
     * 银行卡Pos机支付出货  |支付金额|货道号|支付方式|卡号|
     */
    public static void shipWithBankCard(String cardNumber, String selectedPrice, String selectedSlotNo) {
        if (null == selectedPrice || null == selectedSlotNo || null == cardNumber) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("|A|");
        sb.append(selectedPrice);
        sb.append("|"+selectedSlotNo);
        sb.append("|5|");
        sb.append(cardNumber);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
     *出货命令
     * 微信支付出货  |支付金额|货道号|支付方式|卡号|
     */
    public static void shipWithWeChatPay(String selectedPrice, String selectedSlotNo) {
        if (null == selectedPrice || null == selectedSlotNo) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("|A|");
        sb.append(selectedPrice);
        sb.append("|"+selectedSlotNo);
        sb.append("|11|");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
     *出货命令
     * 支付宝支付出货   |支付金额|货道号|支付方式|卡号|
     */
    public static void shipWithAliPay(String selectedPrice, String selectedSlotNo) {
        if (null == selectedPrice || null == selectedSlotNo) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("|A|");
        sb.append(selectedPrice);
        sb.append("|"+selectedSlotNo);
        sb.append("|13|");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
     *出货命令
     * 赠送出货  |支付金额|货道号|支付方式|卡号|
     */
    public static void shipWithGifts(String selectedPrice, String selectedSlotNo) {
        if (null == selectedPrice || null == selectedSlotNo) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("|A|");
        sb.append(selectedPrice);
        sb.append("|"+selectedSlotNo);
        sb.append("|15|");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
     *出货命令
     * 赠送出货  |支付金额|货道号|支付方式|卡号|
     */
    public static void shipWithGifts(String price, int slotNo, boolean isVersionMoreThan22) {
        if ((null == price) || (slotNo < 1)) {
            return;
        }
        //解决有可能不出货的问题
        if (isVersionMoreThan22) {
            reqSelectGoodsBySlotNo(slotNo);
        } else {
            reqSelectGoods(slotNo);
        }
        StringBuffer sb = new StringBuffer();
        sb.append("|A|");
        sb.append(price);
        sb.append("|"+slotNo);
        sb.append("|15|");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    *出货命令
    * 远程出货（App一键出货）   |支付金额|货道号|支付方式|卡号|
    */
    public static void shipWithRemout(String selectedPrice, String selectedSlotNo) {
        if (null == selectedPrice || null == selectedSlotNo) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("|A|");
        sb.append(selectedPrice);
        sb.append("|"+selectedSlotNo);
        sb.append("|16|");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    *出货命令
    * 远程出货（App一键出货）   |支付金额|货道号|支付方式|卡号|
    */
    public static void shipWithRemout(String price, int slotNo, boolean isVersionMoreThan22) {
        //解决有可能不出货的问题
        if (isVersionMoreThan22) {
            reqSelectGoodsBySlotNo(slotNo);
        } else {
            reqSelectGoods(slotNo);
        }
        StringBuffer sb = new StringBuffer();
        sb.append("|A|");
        sb.append(price);
        sb.append("|"+slotNo);
        sb.append("|16|");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    *出货命令
    * 验证码出货(提货码出货)   |支付金额|货道号|支付方式|卡号|
    */
    public static void shipWithVerify(String selectedPrice, String selectedSlotNo) {
        if (null == selectedPrice || null == selectedSlotNo) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("|A|");
        sb.append(selectedPrice);
        sb.append("|"+selectedSlotNo);
        sb.append("|17|");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    public static void shipWithVerify(String price, int slotNo, boolean isVersionMoreThan22) {
        //解决有可能不出货的问题
        if (isVersionMoreThan22) {
            reqSelectGoodsBySlotNo(slotNo);
        } else {
            reqSelectGoods(slotNo);
        }
        StringBuffer sb = new StringBuffer();
        sb.append("|A|");
        sb.append(price);
        sb.append("|"+slotNo);
        sb.append("|17|");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }


    /*
    * 获取支付系统信息
    */
    public static void reqPayInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("|B|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 获取货道信息
    */
    public static void reqSlotNoInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("|C|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 获取温度
    */
    public static void reqTemperatureInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("|D|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 获取按键信息
    */
    public static void reqKeyInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("|E|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 获取其它信息
    * 包括 17总货道数 19门状态 31时间及机器ID 32总销售
    */
    public static void reqOtherInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("|F|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 获取当前余额
    */
    public static void reqBalanceInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("|G|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 表示触屏选货 选择商品
    * 命令解析：$$$|H|货道号|BCC%%%   16055.22和16055.22以下
    */
    public static void reqSelectGoods(int slotNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("|H|");
        sb.append(String.valueOf(slotNo));
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    public static void reqSelectGoodsK(int key) {
        StringBuffer sb = new StringBuffer();
        sb.append("|H|");
        sb.append("K");
        sb.append("|");
        sb.append(key);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }


    //确定
    public static void reqSelectSure() {
        StringBuffer sb = new StringBuffer();
        sb.append("|H|");
        sb.append("K");
        sb.append("|");
        sb.append(10);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    //取消
    public static void reqSelectCancel() {
        StringBuffer sb = new StringBuffer();
        sb.append("|H|");
        sb.append("K");
        sb.append("|");
        sb.append(11);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    //16055.23和16055.23以上
    public static void reqSelectGoodsBySlotNo(int slotNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("|H|");
        sb.append("S");
        sb.append("|");
        sb.append(slotNo);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    //取货码取货
    public static void reqTakeGoodsByCode(String code) {
        StringBuffer sb = new StringBuffer();
        sb.append("|H|");
        sb.append("B");
        sb.append("|");
        sb.append(code);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 重新计时
    * 命令解析：$$$|I|时间|BCC%%%
    */
    public static void reqReTime(int time) {
        StringBuffer sb = new StringBuffer();
        sb.append("|I|");
        sb.append(String.valueOf(time));
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 清除余额
    */
    public static void reqCleanBalance() {
        StringBuffer sb = new StringBuffer();
        sb.append("|J|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 退币命令
    * 命令解析：$$$|K|退币金额|BCC%%%
    */
    public static void reqRetureCoin(float amount) {
        StringBuffer sb = new StringBuffer();
        sb.append("|K|");
        sb.append(String.valueOf(amount));
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 上位机连接上服务器了
    * 命令解析：$$$|L|信号强度|BCC%%%  (信号强度0-30)
    */
    public static void reqNetWorkConnected() {
        StringBuffer sb = new StringBuffer();
        sb.append("|L|");
        sb.append("30");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 上位机断开服务器了
    * $$$|L|信号强度|BCC%%%  (信号强度0-30)
    */
    public static void reqNetWorkConnectFail() {
        StringBuffer sb = new StringBuffer();
        sb.append("|L|");
        sb.append("0");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 开LED灯(O:开   C：关)
    * 命令解析：$$$|N|O|BCC%%%
    */
    public static void reqOpenLight() {
        StringBuffer sb = new StringBuffer();
        sb.append("|M|");
        sb.append("O");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 关LED灯
    * 命令解析：$$$|N|C|BCC%%%
    */
    public static void reqCloseLight() {
        StringBuffer sb = new StringBuffer();
        sb.append("|M|");
        sb.append("C");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * C制冷 H加热 S停机
    * 命令解析：$$$|O|O/C|BCC%%%
    */
    public static void reqOpenCool() {
        StringBuffer sb = new StringBuffer();
        sb.append("|N|");
        sb.append("C");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    public static void reqOpenHeat(boolean open) {
        StringBuffer sb = new StringBuffer();
        sb.append("|N|");
        sb.append("H");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    public static void reqCloseCoolAndHeat() {
        StringBuffer sb = new StringBuffer();
        sb.append("|N|");
        sb.append("S");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读存储语言
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadLanguage() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("1");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改存储语言     language:  0:英文   1：中文
    * 命令解析：$$$|P|菜单号|语言|BCC%%%
    */
    public static void reqWriteLanguage(int language) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("1");
        sb.append("|");
        sb.append(language);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读取整机掉货检测开关状态  0表示关掉  1表示打开
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadDropSensorWhole() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("2");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 设置整机掉货检测开关     status:  0表示关掉    1表示打开
    * 命令解析：$$$|P|菜单号|开关状态|BCC%%%
    */
    public static void reqWriteDropSensorWhole(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("2");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读取纸币是否可以退回？ (0表示不可以, 1表示可以)
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadBillEscrowEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("3");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 设置纸币是否可以退回？ (0表示不可以, 1表示可以)
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteBillEscrowEnable(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("3");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读取硬币是否可找零 (0表示不可以, 1表示可以)
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadCoinChange() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("4");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 设置硬币是否可找零 (0表示不可以, 1表示可以)
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteCoinChange(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("4");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读取温度  1区
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadTemperatureFirst() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("5");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 设置温度  1区
    * 命令解析：$$$|P|菜单号|温度|BCC%%%
    */
    public static void reqWriteTemperatureFirst(String temp) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("5");
        sb.append("|");
        sb.append(temp);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读取温度  2区
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadTemperatureSecond() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("6");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 设置温度  2区
    * 命令解析：$$$|P|菜单号|温度|BCC%%%
    */
    public static void reqWriteTemperatureSecond(String temp) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("6");
        sb.append("|");
        sb.append(temp);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读取制冷加热系统控制  如果为0表示制冷 为1表示加热 为2表示关闭     1区
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadCoolSystemEnableFirst() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("7");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 设置制冷加热系统控制  如果为0表示制冷 为1表示加热 为2表示关闭     1区
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteCoolSystemEnableFirst(String status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("7");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读取制冷加热系统控制  如果为0表示制冷 为1表示加热 为2表示关闭     2区
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadCoolSystemEnableSecond() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("8");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 设置制冷加热系统控制  如果为0表示制冷 为1表示加热 为2表示关闭     2区
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteCoolSystemEnableSecond(String status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("8");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读取允许温度上限
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadTemUpLimit() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("9");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 设置允许温度上限
    * 命令解析：$$$|P|菜单号|温度|BCC%%%
    */
    public static void reqWriteTempUpLimit(String temp) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("9");
        sb.append("|");
        sb.append(temp);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读取允许温度下限
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadTempFloorLimit() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("10");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 设置允许温度下限
    * 命令解析：$$$|P|菜单号|温度|BCC%%%
    */
    public static void reqWriteTempFloorLimit(String temp) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("10");
        sb.append("|");
        sb.append(temp);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读取存储出货检测故障  连续多次转动正常但没检测到商品掉下来 则为1 如果没有则为0
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadSlotOKDropSensorFault() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("11");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 设置 存储出货检测故障  连续多次转动正常但没检测到商品掉下来 则为1 如果没有则为0
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteSlotOKDropSensorFault(String status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("11");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读取设置多少次故障就锁定机器  也可能是货道故障 也可能是光电开关没检测到商品    9表示不锁定
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadFaultCountLock() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("12");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 设置多少次故障就锁定机器  也可能是货道故障 也可能是光电开关没检测到商品    9表示不锁定
    * 命令解析：$$$|P|菜单号|故障次数|BCC%%%
    */
    public static void reqWriteFaultCountLock(int faultCount) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("12");
        sb.append("|");
        sb.append(faultCount);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读取存储是否直接找零 如果为1表示直接找零  如果为0表示需要按键才找零
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadChangeDirect() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("13");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 设置存储是否直接找零 如果为1表示直接找零  如果为0表示需要按键才找零
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteChangeDirect(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("13");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读取没有出货采取的处理   0表示直接退币
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadVendFaultGiveChange() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("14");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 设置没有出货采取的处理   0表示直接退币
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteVendFaultGiveChange(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("14");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读玻璃除雾        0:关闭    1：打开
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadGlassDemistEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("15");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改玻璃除雾状态     status:  0:关闭    1：打开
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteGlassDemistEnable(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("15");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读坡形货道  掉货检测灵敏度
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadDropSensorFirstValue() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("16");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改坡形货道  掉货检测灵敏度
    * 命令解析：$$$|P|菜单号|灵敏度|BCC%%%
    */
    public static void reqWriteDropSensorFirstValue(String value) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("16");
        sb.append("|");
        sb.append(value);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读坡形货道  掉货检测灵敏度
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadDropSensorSecondValue() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("17");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改坡形货道  掉货检测灵敏度
    * 命令解析：$$$|P|菜单号|灵敏度|BCC%%%
    */
    public static void reqWriteDropSensorSecondValue(String value) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("17");
        sb.append("|");
        sb.append(value);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读坡形货道  掉货检测灵敏度
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadDropSensorThirdValue() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("18");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改坡形货道  掉货检测灵敏度
    * 命令解析：$$$|P|菜单号|灵敏度|BCC%%%
    */
    public static void reqWriteDropSensorThirdValue(String value) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("18");
        sb.append("|");
        sb.append(value);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读第一副柜门数  如果大于17 那么就不使用么二副柜 第二副柜只能空掉
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadCabinetMaxSlotNum() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("19");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改第一副柜门数  如果大于17 那么就不使用么二副柜 第二副柜只能空掉
    * 命令解析：$$$|P|菜单号|门数|BCC%%%
    */
    public static void reqWriteCabinetMaxSlotNum(String num) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("19");
        sb.append("|");
        sb.append(num);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读是否使用IC卡  如果为1表示带脱机使用IC卡  如果为2表示带联网使用IC卡
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadICCardEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("20");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改是否使用IC卡  如果为1表示带脱机使用IC卡  如果为2表示带联网使用IC卡
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteICCardEnable(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("20");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读如果为1表示带有GPRS模块 不能发送与电脑通信的命令
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadGprsModuleEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("21");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改如果为1表示带有GPRS模块 不能发送与电脑通信的命令
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteGprsModuleEnable(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("21");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读不买货是否可以找零
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadNoSaleChangeEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("22");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改不买货是否可以找零
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteNoSaleChangeEnable(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("22");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 余额是否清除，设置为1-9 如果为9表示不清除 如果为1-8表示1-8分钟清除
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadClearBalanceTime() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("23");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 余额是否清除，设置为1-9 如果为9表示不清除 如果为1-8 表示1-8分钟清除
    * 命令解析：$$$|P|菜单号|时间|BCC%%%
    */
    public static void reqWriteClearBalanceTime(int time) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("23");
        sb.append("|");
        sb.append(time);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 如果温度在设定范围以外，多久停止使用，1-9 如果设置为9表示不停止使用  如果设置为其它值 则表示多少个小时停止使用
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadTempOutRangeStopTime() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("24");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 如果温度在设定范围以外，多久停止使用，1-9 如果设置为9表示不停止使用  如果设置为其它值 则表示多少个小时停止使用
    * 命令解析：$$$|P|菜单号|时间|BCC%%%
    */
    public static void reqWriteTempOutRangeStopTime(int time) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("24");
        sb.append("|");
        sb.append(time);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 APP出货开关  0:关    1：开
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadAppVendEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("26");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 APP出货开关  0:关    1：开
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteAppVendEnable(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("26");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 如果存储记录满了 是否停止工作
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadRecordFullClosEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("27");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 如果存储记录满了 是否停止工作
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteRecordFullClosEnable(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("27");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 如果零钱不足了 禁止纸币
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadLowChangeDisableBill() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("28");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 如果零钱不足了 禁止纸币
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteLowChangeDisableBill(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("28");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 是否带咖啡机
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadHaveCoffeeVend() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("29");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 是否带咖啡机
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteHaveCoffeeVend(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("29");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 总柜数
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadTotalCabinetNum() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("30");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 总柜数
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteTotalCabinetNum(int num) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("30");
        sb.append("|");
        sb.append(num);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 如果为0表示没有POS机  如果是1表示朗方290  2表示升途SD-XP100
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadPosType() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("31");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 如果为0表示没有POS机  如果是1表示朗方290  2表示升途SD-XP100
    * 命令解析：$$$|P|菜单号|POS机|BCC%%%
    */
    public static void reqWritePosType(int type) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("31");
        sb.append("|");
        sb.append(type);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 充值货道号
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadRechargeSlotNo() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("32");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 充值货道号
    * 命令解析：$$$|P|菜单号|货道号|BCC%%%
    */
    public static void reqWriteRechargeSlotNo(int slotNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("32");
        sb.append("|");
        sb.append(slotNo);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 振动时间
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadShockTime() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("33");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 振动时间
    * 命令解析：$$$|P|菜单号|振动时间|BCC%%%
    */
    public static void reqWriteShockTime(int time) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("33");
        sb.append("|");
        sb.append(time);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 做为振动电机的货道号		如果为0表示不振动
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadShakeMotorNum() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("34");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 做为振动电机的货道号		如果为0表示不振动
    * 命令解析：$$$|P|菜单号|货道号|BCC%%%
    */
    public static void reqWriteShakeMotorNum(int slotNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("34");
        sb.append("|");
        sb.append(slotNo);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 如果温度高出设置值超过设定的小时数 就停止使用机器 同时写入标志 重新通电也显示
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadOutOfService() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("35");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 如果温度高出设置值超过设定的小时数 就停止使用机器 同时写入标志 重新通电也显示
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteOutOfService(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("35");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 是否提示选择支付方式
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadShowOptionPayment() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("36");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 是否提示选择支付方式
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteShowOptionPayment(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("36");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 赠送商品缺货时是否停止购买
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadNoDonateStopEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("37");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 赠送商品缺货时是否停止购买
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteNoDonateStopEnable(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("37");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 找纸币方式  如果为0表示以金额方式找  如果为1表示以张数找
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadOutBillForm() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("38");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 找纸币方式  如果为0表示以金额方式找  如果为1表示以张数找
    * 命令解析：$$$|P|菜单号|方式|BCC%%%
    */
    public static void reqWriteOutBillForm(int mode) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("38");
        sb.append("|");
        sb.append(mode);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 是否使用遥控出货
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadRemoteControlEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("39");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 是否使用遥控出货
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteRemoteControlEnable(int mode) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("39");
        sb.append("|");
        sb.append(mode);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读支付宝开关状态  status:  0:关闭    1：打开
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadAlipayEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("40");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改支付宝开关状态     status:  0:关闭    1：打开
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteAlipayEnable(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("40");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
   * 读微信开关状态
   * 命令解析：$$$|O|菜单号|BCC%%%
   */
    public static void reqReadWechatEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("41");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改微信开关状态     status:  0:关闭    1：打开
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteWechatEnable(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("41");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 传感器1灵敏度
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadSensorFirstSensitivity() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("52");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 传感器1灵敏度
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteSensorFirstSensitivity(String sensitivity) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("52");
        sb.append("|");
        sb.append(sensitivity);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 传感器2灵敏度
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadSensorSecondSensitivity() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("53");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 传感器2灵敏度
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteSensorSecondSensitivity(String sensitivity) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("53");
        sb.append("|");
        sb.append(sensitivity);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 传感器3灵敏度
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadSensorThirdSensitivity() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("54");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 传感器3灵敏度
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteSensorThirdSensitivity(String sensitivity) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("54");
        sb.append("|");
        sb.append(sensitivity);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 报警输出方式
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadAlarmOutputType() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("55");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 报警输出方式
    * 命令解析：$$$|P|菜单号|输出方式|BCC%%%
    */
    public static void reqWriteAlarmOutputType(String outputType) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("55");
        sb.append("|");
        sb.append(outputType);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 报警输出时间
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadAlarmOutputTime() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("56");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 报警输出时间
    * 命令解析：$$$|P|菜单号|时间|BCC%%%
    */
    public static void reqWriteAlarmOutputTime(String outputTime) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("56");
        sb.append("|");
        sb.append(outputTime);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 报警器开关
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadAlarmEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("57");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 报警器开关
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteAlarmEnable(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("57");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 来电停电提醒开关
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadPowerOnOffWarn() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("58");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 来电停电提醒开关
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWritePowerOnOffWarn(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("58");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 板载驱动器使能
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadDriveEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("59");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 板载驱动器使能
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteDriveEnable(int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("59");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 板载驱动器设置为12货道 还是10货道
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadDrive12X8OR10X8() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("60");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 板载驱动器设置为12货道 还是10货道
    * 命令解析：$$$|P|菜单号|数量|BCC%%%
    */
    public static void reqWriteDrive12X8OR10X8(int num) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("60");
        sb.append("|");
        sb.append(num);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 板载驱动器掉货检测模式		 0表示普通	1升降机光检
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadDriveLiftOrNot() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("61");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 板载驱动器掉货检测模式		 0表示普通	1升降机光检
    * 命令解析：$$$|P|菜单号|模式|BCC%%%
    */
    public static void reqWriteDriveLiftOrNot(int mode) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("61");
        sb.append("|");
        sb.append(mode);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 可接收纸币种类 对应位如果为1 表示可接收
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadBillTypeEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("65");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 可接收纸币种类 对应位如果为1 表示可接收
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteBillTypeEnable(String status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("65");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 可接收硬币种类 对应位如果为1 表示可接收
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadCoinTypeEnable() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("66");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 可接收硬币种类 对应位如果为1 表示可接收
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteCoinTypeEnable(String status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("66");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 低于设置值显示零钱不足
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadLowChangeLimitDisplay() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("67");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 低于设置值显示零钱不足
    * 命令解析：$$$|P|菜单号|状态|BCC%%%
    */
    public static void reqWriteLowChangeLimitDisplay(String status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("67");
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 最大允许接收金额
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadMaxAcceptMoney() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("68");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 最大允许接收金额
    * 命令解析：$$$|P|菜单号|金额|BCC%%%
    */
    public static void reqWriteMaxAcceptMoney(String money) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("68");
        sb.append("|");
        sb.append(money);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 非MDB找零纸币时找出纸币的面额
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadNotMDBBillFaceValue() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("69");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 非MDB找零纸币时找出纸币的面额
    * 命令解析：$$$|P|菜单号|金额|BCC%%%
    */
    public static void reqWriteNotMDBBillFaceValue(String money) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("69");
        sb.append("|");
        sb.append(money);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
   * 读机出货检测开关  0:关闭    1：打开
   * 命令解析：$$$|O|菜单号|货道号|BCC%%%
   */
    public static void reqReadDropSensor(int slotNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("70");
        sb.append("|");
        sb.append(slotNo);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    *只适用于16055.22之后的版本（包括16055.22）
    * 改出货检测开关     status:  0:关闭    1：打开
    * 命令解析：$$$|P|菜单号|起始货道号|结束货道号|状态|BCC%%%
    */
    public static void reqWriteDropSensor(int startSlotNo, int endSlotNo, int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("70");
        sb.append("|");
        sb.append(startSlotNo);
        sb.append("|");
        sb.append(endSlotNo);
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读机器货道容量
    * 命令解析：$$$|O|菜单号|货道号|BCC%%%
    */
    public static void reqReadSlotCapacity(int slotNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("71");
        sb.append("|");
        sb.append(slotNo);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本（包括16055.22）
    * 改机器货道容量
    * 命令解析：$$$|P|菜单号|起始货道号|结束货道号|容量|BCC%%%
    */
    public static void reqWriteSlotCapacity(int startSlotNo, int endSlotNo, int capacity) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("71");
        sb.append("|");
        sb.append(startSlotNo);
        sb.append("|");
        sb.append(endSlotNo);
        sb.append("|");
        sb.append(capacity);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读机器货道商品现存数量
    * 命令解析：$$$|O|菜单号|货道号|BCC%%%
    */
    public static void reqReadSlotStock(int slotNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("72");
        sb.append("|");
        sb.append(slotNo);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本（包括16055.22）
    * 改机器货道商品现存数量
    * 命令解析：$$$|P|菜单号|起始货道号|结束货道号|现存数量|BCC%%%
    */
    public static void reqWriteSlotStock(int startSlotNo, int endSlotNo, int stock) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("72");
        sb.append("|");
        sb.append(startSlotNo);
        sb.append("|");
        sb.append(endSlotNo);
        sb.append("|");
        sb.append(stock);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读机器货道工作状态（是否故障）
    * 命令解析：$$$|O|菜单号|货道号|BCC%%%
    */
    public static void reqReadWorkStatus(int slotNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("73");
        sb.append("|");
        sb.append(slotNo);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本（包括16055.22）
    * 改机器货道工作状态（是否故障）
    * 命令解析：$$$|P|菜单号|货道号|工作状态|BCC%%%    工作状态  写0：表示清除故障
    */
    public static void reqWriteWorkStatus(int startSlotNo, int endSlotNo, int status) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("73");
        sb.append("|");
        sb.append(startSlotNo);
        sb.append("|");
        sb.append(endSlotNo);
        sb.append("|");
        sb.append(status);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读机器货道单价
    * 命令解析：$$$|O|菜单号|货道号|BCC%%%
    */
    public static void reqReadSlotPrice(int slotNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("74");
        sb.append("|");
        sb.append(slotNo);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本（包括16055.22）
    * 改机器货道单价
    * 命令解析：$$$|P|菜单号|货道号|单价|BCC%%%
    */
    public static void reqWriteSlotPrice(int startSlotNo, int endSlotNo, String price) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("74");
        sb.append("|");
        sb.append(startSlotNo);
        sb.append("|");
        sb.append(endSlotNo);
        sb.append("|");
        sb.append(price);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读机器货道出货次数
    * 命令解析：$$$|O|菜单号|货道号|BCC%%%
    */
    public static void reqReadSaleVolume(int slotNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("75");
        sb.append("|");
        sb.append(slotNo);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改机器货道出货次数
    * 命令解析：$$$|P|菜单号|货道号|出货次数|BCC%%%
    */
    public static void reqWriteSaleVolume(int slotNo, int num) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("75");
        sb.append("|");
        sb.append(slotNo);
        sb.append("|");
        sb.append(num);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本（包括16055.22）
    * 改机器货道出货次数
    * 命令解析：$$$|P|菜单号|货道号|出货次数|BCC%%%
    */
    public static void reqWriteSaleVolume(int startSlotNo, int endSlotNo, int num) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("75");
        sb.append("|");
        sb.append(startSlotNo);
        sb.append("|");
        sb.append(endSlotNo);
        sb.append("|");
        sb.append(num);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读机器货道销售金额
    * 命令解析：$$$|O|菜单号|货道号|BCC%%%
    */
    public static void reqReadSaleAmount(int slotNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("76");
        sb.append("|");
        sb.append(slotNo);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改机器货道销售金额
    * 命令解析：$$$|P|菜单号|货道号|销售金额|BCC%%%
    */
    public static void reqWriteSaleAmount(int slotNo, String amount) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("76");
        sb.append("|");
        sb.append(slotNo);
        sb.append("|");
        sb.append(amount);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本（包括16055.22）
    * 改机器货道销售金额
    * 命令解析：$$$|P|菜单号|货道号|销售金额|BCC%%%
    */
    public static void reqWriteSaleAmount(int startSlotNo, int endSlotNo, String amount) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("76");
        sb.append("|");
        sb.append(startSlotNo);
        sb.append("|");
        sb.append(endSlotNo);
        sb.append("|");
        sb.append(amount);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
 * 读 报警器存储手机号码  15位
 * 命令解析：$$$|O|菜单号|BCC%%%
 */
    public static void reqReadAlarmFirstMobileNum() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("86");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 报警器存储手机号码  15位
    * 命令解析：$$$|P|菜单号|号码|BCC%%%
    */
    public static void reqWriteAlarmFirstMobileNum(String num) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("86");
        sb.append("|");
        sb.append(num);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
  * 读 报警器存储手机号码  15位
  * 命令解析：$$$|O|菜单号|BCC%%%
  */
    public static void reqReadAlarmSecondMobileNum() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("87");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 报警器存储手机号码  15位
    * 命令解析：$$$|P|菜单号|号码|BCC%%%
    */
    public static void reqWriteAlarmSecondMobileNum(String num) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("87");
        sb.append("|");
        sb.append(num);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
     * 读 报警器存储手机号码  15位
     * 命令解析：$$$|O|菜单号|BCC%%%
     */
    public static void reqReadAlarmThirdMobileNum() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("88");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 报警器存储手机号码  15位
    * 命令解析：$$$|P|菜单号|号码|BCC%%%
    */
    public static void reqWriteAlarmThirdMobileNum(String num) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("88");
        sb.append("|");
        sb.append(num);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读 存储100个板载驱动电机皮带的步数
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqRead100SlotPulse() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("90");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改 存储100个板载驱动电机皮带的步数
    * 命令解析：$$$|P|菜单号|步数|BCC%%%
    */
    public static void reqWrite100SlotPulse(int num) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("90");
        sb.append("|");
        sb.append(num);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 清空纸币找零区
    * 命令解析：$$$|P|菜单号|BCC%%%
    */
    public static void reqClearPaperMoneyBalance() {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("92");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 清除销售记录
    * 命令解析：$$$|P|菜单号|BCC%%%
    */
    public static void reqClearSalesRecord() {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("93");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 读机器货道商品编码
    * 命令解析：$$$|O|菜单号|起始货道号|结束货道号|BCC%%%
    */
    public static void reqReadGoodsCode(String startSlotNo, String endSlotNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("94");
        sb.append("|");
        sb.append(startSlotNo);
        sb.append("|");
        sb.append(endSlotNo);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 改机器货道商品编码
    * 命令解析：$$$|P|菜单号|起始货道号|结束货道号|商品编码|BCC%%%
    */
    public static void reqWriteGoodsCode(int startSlotNo, int endSlotNo, String code) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("94");
        sb.append("|");
        sb.append(startSlotNo);
        sb.append("|");
        sb.append(endSlotNo);
        sb.append("|");
        sb.append(code);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本
    * 测试货道
    * 命令解析：$$$|P|菜单号|货道号|BCC%%%
    */
    public static void reqWriteTestMotor(int slotNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("96");
        sb.append("|");
        sb.append(slotNo);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本
    * 修改时间  //例如参数为  16|08|31|20|18|30
    * 命令解析：$$$|P|菜单号|时间|BCC%%%
    */
    public static void reqReadTime() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("97");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本
    * 修改时间  //例如参数为  16|08|31|20|18|30
    * 命令解析：$$$|P|菜单号|时间|BCC%%%
    */
    public static void reqWriteTime(String time) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("97");
        sb.append("|");
        sb.append(time);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本
    * 开始上货 // 参数为 ENTER
    * 命令解析：$$$|P|菜单号|ENTER|BCC%%%
    */
    public static void reqWriteStartCashFill() {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("98");
        sb.append("|");
        sb.append("ENTER");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本
    * 结束上货 // 参数为 EXIT
    * 命令解析：$$$|P|菜单号|EXIT|BCC%%%
    */
    public static void reqWriteEndCashFill() {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("98");
        sb.append("|");
        sb.append("EXIT");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本
    * 赠送商品   参数：货道号|赠送货道号
    * 命令解析：$$$|P|菜单号|EXIT|BCC%%%
    */
    public static void reqWriteDonateItem(int slotNo,int slotNoGiven) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("99");
        sb.append("|");
        sb.append(slotNo);
        sb.append("|");
        sb.append(slotNoGiven);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本
    * 读机器赠送商品
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadDonateItem() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("99");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本
    * 合并货道
    * 命令解析：$$$|P|菜单号|货道号|BCC%%%
    */
    public static void reqWriteMergeSlot(int mainSlotNo,int beBoundSlotNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("|P|");
        sb.append("100");
        sb.append("|");
        sb.append(mainSlotNo);
        sb.append("|");
        sb.append(beBoundSlotNo);
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    /*
    * 只适用于16055.22之后的版本
    * 读出所有的合并货道的信息 例如返回：$$$|35|100|1-2|889%%%
    * 命令解析：$$$|O|菜单号|BCC%%%
    */
    public static void reqReadMergeSlot() {
        StringBuffer sb = new StringBuffer();
        sb.append("|O|");
        sb.append("100");
        sb.append("|");
        String content="$$$"+sb.toString()+Utility.getCheckSum(sb.toString())+"%%%";
        writeData(content);
    }

    public static void writeData(String data) {
//        if (m_bIsDoorOpen) {
//            if (m_handler != null) {
//                m_handler.removeMessages(COMMAND_DOOR_IS_OPEND);
//                m_handler.sendEmptyMessage(COMMAND_DOOR_IS_OPEND);
//            }
//            return;
//        }
        SerialPortController.getInstance().writeDataImmediately(data);
    }
}

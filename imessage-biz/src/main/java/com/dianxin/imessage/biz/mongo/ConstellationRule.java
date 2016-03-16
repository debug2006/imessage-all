package com.dianxin.imessage.biz.mongo;

import com.dianxin.imessage.common.lbs.data.MongoPosition;
import com.dianxin.imessage.common.util.DateUtil;
import com.dianxin.imessage.dao.dataobject.DxUserModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 星座：具体分值情况如下表。在这里注意，如果同一天生日，则自动赋予25分。如果同年同月同日生，则赋予50分
 * 注：该数据由星座匹配度百分比计算而来。星座匹配度百分比综合了几份配对表的数据。
 * 将配对数据标准化之后取PERCENTRANK.INC，之后与25分的满分相乘，得出下表。
 * 主要是为了把最高配对的人加25分，最低配对的人加0分
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/2/1
 * Time: 10:58
 */
public class ConstellationRule {

    private static Double[][] constelArray = new Double[13][13];

    static
    {
        constelArray[0][0] = 0.00;constelArray[1][0] = 0.00;constelArray[0][1] = 0.00;
        constelArray[1][1] = 22.55;constelArray[1][2] = 8.74;constelArray[1][3] = 15.56;constelArray[1][4] = 3.85;constelArray[1][5] = 25.00;constelArray[1][6] = 1.75;constelArray[1][7] = 17.83;constelArray[1][8] = 7.34;constelArray[1][9] = 23.95;constelArray[1][10] = 3.85;constelArray[1][11] = 12.06;constelArray[1][12] = 1.05;
        constelArray[2][1] = 8.74;constelArray[2][2] = 16.26;constelArray[2][3] = 17.31;constelArray[2][4] = 9.44;constelArray[2][5] = 0.70;constelArray[2][6] = 22.55;constelArray[2][7] = 10.14;constelArray[2][8] = 19.41;constelArray[2][9] = 22.03;constelArray[2][10] = 12.06;constelArray[2][11] = 13.11;constelArray[2][12] = 16.26;
        constelArray[3][1] = 15.56;constelArray[3][2] = 20.28;constelArray[3][3] = 22.03;constelArray[3][4] = 12.76;constelArray[3][5] = 10.14;constelArray[3][6] = 3.85;constelArray[3][7] = 21.15;constelArray[3][8] = 8.39;constelArray[3][9] = 13.11;constelArray[3][10] = 3.50;constelArray[3][11] = 7.34;constelArray[3][12] = 3.85;
        constelArray[4][1] = 3.85;constelArray[4][2] = 9.44;constelArray[4][3] = 12.76;constelArray[4][4] = 20.28;constelArray[4][5] = 1.75;constelArray[4][6] = 13.11;constelArray[4][7] = 0.35;constelArray[4][8] = 13.11;constelArray[4][9] = 10.14;constelArray[4][10] = 23.60;constelArray[4][11] = 13.11;constelArray[4][12] = 20.28;
        constelArray[5][1] = 25.00;constelArray[5][2] = 0.70;constelArray[5][3] = 10.14;constelArray[5][4] = 1.75;constelArray[5][5] = 19.41;constelArray[5][6] = 3.85;constelArray[5][7] = 18.18;constelArray[5][8] = 0.00;constelArray[5][9] = 19.41;constelArray[5][10] = 7.34;constelArray[5][11] = 3.85;constelArray[5][12] = 3.15;
        constelArray[6][1] = 1.75;constelArray[6][2] = 22.55;constelArray[6][3] = 3.85;constelArray[6][4] = 13.11;constelArray[6][5] = 3.85;constelArray[6][6] = 22.55;constelArray[6][7] = 3.85;constelArray[6][8] = 13.11;constelArray[6][9] = 9.79;constelArray[6][10] = 21.15;constelArray[6][11] = 1.05;constelArray[6][12] = 16.96;
        constelArray[7][1] = 17.83;constelArray[7][2] = 10.14;constelArray[7][3] = 21.15;constelArray[7][4] = 0.35;constelArray[7][5] = 18.18;constelArray[7][6] = 3.85;constelArray[7][7] = 21.15;constelArray[7][8] = 3.85;constelArray[7][9] = 13.11;constelArray[7][10] = 2.45;constelArray[7][11] = 23.95;constelArray[7][12] = 6.99;
        constelArray[8][1] = 7.34;constelArray[8][2] = 19.41;constelArray[8][3] = 8.39;constelArray[8][4] = 13.11;constelArray[8][5] = 0.00;constelArray[8][6] = 13.11;constelArray[8][7] = 3.85;constelArray[8][8] = 10.14;constelArray[8][9] = 15.56;constelArray[8][10] = 17.31;constelArray[8][11] = 18.18;constelArray[8][12] = 22.55;
        constelArray[9][1] = 23.95;constelArray[9][2] = 22.03;constelArray[9][3] = 13.11;constelArray[9][4] = 10.14;constelArray[9][5] = 19.41;constelArray[9][6] = 9.79;constelArray[9][7] = 13.11;constelArray[9][8] = 15.56;constelArray[9][9] = 20.28;constelArray[9][10] = 3.85;constelArray[9][11] = 8.74;constelArray[9][12] = 2.45;
        constelArray[10][1] = 3.85;constelArray[10][2] = 12.06;constelArray[10][3] = 3.50;constelArray[10][4] = 23.60;constelArray[10][5] = 7.34;constelArray[10][6] = 21.15;constelArray[10][7] = 2.45;constelArray[10][8] = 17.31;constelArray[10][9] = 3.85;constelArray[10][10] = 16.26;constelArray[10][11] = 11.71;constelArray[10][12] = 18.18;
        constelArray[11][1] = 12.06;constelArray[11][2] = 13.11;constelArray[11][3] = 7.34;constelArray[11][4] = 13.11;constelArray[11][5] = 3.85;constelArray[11][6] = 1.05;constelArray[11][7] = 23.95;constelArray[11][8] = 18.18;constelArray[11][9] = 8.74;constelArray[11][10] = 11.71;constelArray[11][11] = 18.18;constelArray[11][12] = 10.14;
        constelArray[12][1] = 1.05;constelArray[12][2] = 16.26;constelArray[12][3] = 3.85;constelArray[12][4] = 20.28;constelArray[12][5] = 3.15;constelArray[12][6] = 16.96;constelArray[12][7] = 6.99;constelArray[12][8] = 22.55;constelArray[12][9] = 2.45;constelArray[12][10] = 18.18;constelArray[12][11] = 10.14;constelArray[12][12] = 24.50;
    }

    /**
     * 计算星座得分
     * @param mongoPosition
     * @param user
     * @param toUser
     * @return
     */
    public static double getScoreByConstellation(MongoPosition mongoPosition,DxUserModel user,DxUserModel toUser)
    {
        double score = 0.00;

        try
        {
            String userDate = user.getBirthday();
            String toUserDate = toUser.getBirthday();

            Integer userCon = 0;
            Integer toUserCon = 0;
            if(null!=user.getConstellationType())
            {
                userCon = Integer.valueOf(user.getConstellationType());
            }
            if(null!=toUser.getConstellationType())
            {
                toUserCon = Integer.valueOf(toUser.getConstellationType());
            }


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            if(null!=userDate&&null!=toUserDate)
            {
                Date userDate_ = sdf.parse(userDate);
                Date toUserDate_ = sdf.parse(toUserDate);

                //如果同一天生日，则自动赋予25分。如果同年同月同日生，则赋予50分
                if(DateUtil.isSameDate(userDate_,toUserDate_))
                {
                    score = 50;  //同年同月同日
                }else if(DateUtil.isSameDays(userDate_,toUserDate_))
                {
                    score = 25;  //同一天生日
                }
                else{
                    score = constelArray[userCon][toUserCon];
                }
            }


        }catch (Exception e)
        {
            score = 0;
            e.printStackTrace();

        }

        mongoPosition.setFit(String.valueOf(score));   //星座匹配度

        return score;

    }
}

package com.android.framework.webview.protocolv1;

import android.util.Log;

import com.android.framework.webview.IMikeProtocol;
import com.android.framework.webview.IMikeProtocolAnt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 16/5/15.
 */
public class MikeProtocolV1 implements IMikeProtocol {

    List<IMikeProtocolAnt> mikeProtocolAntList = new ArrayList<>();


    public IMikeProtocol registerProtocolAnt(IMikeProtocolAnt iMikeProtocolAnt) {
        mikeProtocolAntList.add(iMikeProtocolAnt);
        return this;
    }


    @Override
    public String handleProtocol(String url) {

        for (int i = 0; i < mikeProtocolAntList.size(); i++) {
            mikeProtocolAntList.get(i).tryHandler(url);
        }

        Log.e("MikeProtocolV1", "handleProtocol=" + url);


        return "被处理之后的数据" + url;
    }

}

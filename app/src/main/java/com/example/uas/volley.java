package com.example.uas;


import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class volley {
    private RequestQueue requestqueue;
    private static volley minstance;

    private volley(Context context)
    {
        requestqueue = Volley.newRequestQueue(context.getApplicationContext());

    }

    public static synchronized volley getMinstance(Context context)
    {
        if (minstance == null)
        {
            minstance = new volley(context);

        }
        return minstance;
    }
    public RequestQueue getRequestqueue()
    {
        return requestqueue;
    }
}

package com.virtualcode7ecuador.puercos.FragmentMaster;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.virtualcode7ecuador.puercos.R;
public class SeguimientoMasterFragment extends Fragment
{
    private View view_;
    public SeguimientoMasterFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view_ = inflater.inflate(R.layout.fragment_list_edit_citas_master, container, false);
        return view_;
    }
}
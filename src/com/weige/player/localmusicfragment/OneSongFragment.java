package com.weige.player.localmusicfragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;

import com.weige.player.R;
import com.weige.player.adapter.AlphabatOrderAdapter;
import com.weige.player.adapter.MusicShowAdapter;
import com.weige.player.adapter.Spinneradapter;

public class OneSongFragment extends Fragment implements OnItemSelectedListener {
     private Spinner sp_local_music;
	private Spinneradapter adapter;
	private ListView lv_show_song_name;
	private ListView lv_alphabet_order;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
         View view=inflater.inflate( R.layout.local_music_single_song, null);
         initview(view);
         initdata();
         return view;
    }
	public void initview(View view ){
		 sp_local_music = (Spinner) view.findViewById(R.id.sp_local_music);
		 lv_show_song_name = (ListView) view.findViewById(R.id.lv_show_song_name);
		 lv_alphabet_order = (ListView) view.findViewById(R.id.lv_alphabet_order);

	}
	public void initdata(){
		int[] img=new int[]{R.drawable.order_play,R.drawable.radom_play,R.drawable.cycle_play};
		String [] playtext =new String []{
				"˳�򲥷�","�������","����ѭ��"
		};
		String [] alphabat=new String []{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","#"};
		sp_local_music.setOnItemSelectedListener(this);
		adapter = new Spinneradapter(getActivity(),img,playtext);
		sp_local_music.setAdapter(adapter);
		lv_alphabet_order.setAdapter(new AlphabatOrderAdapter(alphabat, getActivity()));
		Cursor cursor = getActivity().getContentResolver().query(  
			       MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,  
			       MediaStore.Audio.Media.DEFAULT_SORT_ORDER); 
		MusicShowAdapter adapter =new MusicShowAdapter(getActivity(), cursor);
		lv_show_song_name.setAdapter(adapter);
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		adapter.setmark(position);
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
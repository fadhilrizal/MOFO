package com.example.padhil.mofo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ListView lstView;
    MaterialSearchView searchView;
    String[] lstsource = {
            "Tangkuban Perahu",
            "Sinabung",
            "Agung",
            "Krakatau",
            "Merapi",
            "Bromo"
    };
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MOFO");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        lstView = (ListView)findViewById(R.id.lst_view);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lstsource);
        lstView.setAdapter(adapter);
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this,MenuForcastActivity.class);
                switch(position){
                    case 0:
                        intent.putExtra("url","http://api.openweathermap.org/data/2.5/weather?q=Bandung,id&appid=8452ff0282c289a826348b3875b80f51&units=Imperial");
                        intent.putExtra("name","Gunung Tangkuban Perahu");
                        break;
                    case 1:
                        intent.putExtra("url","http://api.openweathermap.org/data/2.5/weather?q=Medan,id&appid=8452ff0282c289a826348b3875b80f51&units=Imperial");
                        intent.putExtra("name","Gunung Sinabung");
                        break;
                    case 2:
                        intent.putExtra("url","http://api.openweathermap.org/data/2.5/weather?q=Denpasar,id&appid=8452ff0282c289a826348b3875b80f51&units=Imperial");
                        intent.putExtra("name","Gunung Agung");
                        break;
                    case 3:
                        intent.putExtra("url","http://api.openweathermap.org/data/2.5/weather?q=Medan,id&appid=8452ff0282c289a826348b3875b80f51&units=Imperial");
                        intent.putExtra("name","Gunung Krakatau");
                        break;
                    case 4:
                        intent.putExtra("url","http://api.openweathermap.org/data/2.5/weather?q=Yogyakarta,id&appid=8452ff0282c289a826348b3875b80f51&units=Imperial");
                        intent.putExtra("name","Gunung Merapi");
                        break;
                    case 5:
                        intent.putExtra("url","http://api.openweathermap.org/data/2.5/weather?q=Malang,id&appid=8452ff0282c289a826348b3875b80f51&units=Imperial");
                        intent.putExtra("name","Gunung Bromo");
                        break;
                        //add more if you have more items in listview
                    //0 is the first item 1 second and so on...
                }
                startActivity(intent);
            }
        });

        searchView = (MaterialSearchView)findViewById(R.id.search_view);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                lstView = (ListView)findViewById(R.id.lst_view);
                ArrayAdapter adapter = new ArrayAdapter(SearchActivity.this,android.R.layout.simple_list_item_1,lstsource);
                lstView.setAdapter(adapter);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()) {
                    List<String> lstFound = new ArrayList<String>();
                    for (String item : lstsource) {
                        if (item.contains(newText))
                            lstFound.add(item);
                    }
                    ArrayAdapter adapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, lstFound);
                    lstView.setAdapter(adapter);
                }else{
                    ArrayAdapter adapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, lstsource);
                    lstView.setAdapter(adapter);
                }
                return true;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }
}

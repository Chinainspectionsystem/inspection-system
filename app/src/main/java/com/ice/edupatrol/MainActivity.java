package com.ice.edupatrol;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.xutils.image.ImageOptions;
import org.xutils.x;

public class MainActivity extends AppCompatActivity {
    public String myusername;
    public String myteaching_id="1111";
    public String myteaching_time;
    ActionBarDrawerToggle mDrawerToggle;
    public void setMyteaching_time(String myteaching_time) {
        this.myteaching_time = myteaching_time;
    }

    public String getMyteaching_time() {

        return myteaching_time;
    }

    public String getMyusername() {
        return myusername;
    }

    public String getMyteaching_id() {
        return myteaching_id;
    }

    public void setMyusername(String myusername) {
        this.myusername = myusername;
    }

    public void setMyteaching_id(String myteaching_id) {
        this.myteaching_id = myteaching_id;
    }

    ImageView imageView;
    ListView listView;
    DrawerLayout drawerLayout;
    LinearLayout linearLayout;
    ImageButton btn1;
    ImageButton btn_img2;
    ImageButton btn_img3;
    TextView title;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setCustomActionBar(getSupportActionBar());//绑定ActionBar
        myusername=((MyApplication)getApplication()).getName();
        imageView= (ImageView) findViewById(R.id.image_tx);
        btn_img2= (ImageButton) findViewById(R.id.own1);
        btn_img3= (ImageButton) findViewById(R.id.own2);
        listView = (ListView) findViewById(R.id.listView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        linearLayout = (LinearLayout) findViewById(R.id.drawerLayout_l);
        btn1= (ImageButton) findViewById(R.id.own);
        setCustomActionBar(getSupportActionBar());
        btn1.setImageResource(R.drawable.btnp_main_1);
        String myurl=((MyApplication) getApplication()).getUrl()+((MyApplication)getApplication()).getName()+".png";
        ImageOptions options = new ImageOptions.Builder()
                .setConfig(Bitmap.Config.RGB_565)//设置图片质量,这个是默认的
                .setSquare(true)
                .setCrop(true)//设置图片大小
                .setSize(100,100)//设置图片大小
                .setFadeIn(true)//淡入效果
                .setCircular(true)//展示为圆形
                .build();
        x.image().bind(imageView,myurl,options);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainPanel, new MessageFragment());
        fragmentTransaction.commit();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                setCustomActionBar(getSupportActionBar());
                btn1.setImageResource(R.drawable.btnp_main_1);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainPanel, new MessageFragment());
                fragmentTransaction.commit();
            }
        });
        btn_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                setCustomActionBar1(getSupportActionBar());
                btn_img2.setImageResource(R.drawable.btnp_main_2);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainPanel, new FindFragment());
                fragmentTransaction.commit();
            }
        });
        btn_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                setCustomActionBar2(getSupportActionBar());
                btn_img3.setImageResource(R.drawable.btnp_main_3);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainPanel, new AnalyzeFragment());
                fragmentTransaction.commit();
            }

        });
imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent =new Intent();
        intent.setClass(MainActivity.this,ChangeimageActivity.class);
        startActivity(intent);
    }
});
        String arr[] = new String[]{"                完善信息","                巡查记录", "                手势密码", "                更换头像","                修改密码","                检查更新","                退出登录"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (position == 0)
                {
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,PersonActivity.class);
                    startActivity(intent);
                }
                if (position == 1)
                {
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,RecordActivity.class);
                    startActivity(intent);
                }

                if (position == 2)
                {
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,GestureActivity.class);
                    startActivity(intent);
                }
                if (position == 3)
                {
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,ChangeimageActivity.class);
                    startActivity(intent);
                }
                if (position == 4)
                {
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,ChangepwdActivity.class);
                    startActivity(intent);
                }
                if (position == 5)
                {
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,UpdateActivity.class);
                    startActivity(intent);
                }
                if (position == 6)
                {
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(linearLayout);
            }
        });

    }
public void reset() {
    btn1.setImageResource(R.drawable.btn_main_1);
    btn_img2.setImageResource(R.drawable.btn_main_2);
    btn_img3.setImageResource(R.drawable.btn_main_3);
}
    private  void  setCustomActionBar(ActionBar actionBar){
        ActionBar.LayoutParams lp=new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView= LayoutInflater.from(this).inflate(R.layout.actionbar_min,null);
        if(actionBar!=null) {
            actionBar.setCustomView(mActionBarView, lp);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.bg_actionbar2));
            actionBar.setElevation(0);
        }
    }
    private  void  setCustomActionBar1(ActionBar actionBar){
        ActionBar.LayoutParams lp=new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView= LayoutInflater.from(this).inflate(R.layout.actionbar_min1,null);
        if(actionBar!=null) {
            actionBar.setCustomView(mActionBarView, lp);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.bg_actionbar2));
            actionBar.setElevation(0);
        }
    }
    private  void  setCustomActionBar2(ActionBar actionBar){
        ActionBar.LayoutParams lp=new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView= LayoutInflater.from(this).inflate(R.layout.actionbar_min2,null);
        if(actionBar!=null) {
            actionBar.setCustomView(mActionBarView, lp);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.bg_actionbar2));
            actionBar.setElevation(0);
        }
    }
    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.actionbar,menu);
                return super.onCreateOptionsMenu(menu);
            }
    public boolean onOptionsItemSelected(MenuItem item) {
              switch (item.getItemId()){
                       case R.id.action_edit:
                           drawerLayout.openDrawer(linearLayout);
                            break;
                  case R.id.new2:   Intent intent1 =new Intent();
                      intent1.setClass(MainActivity.this,LoginActivity.class);
                      startActivity(intent1);
                      break;
                  case R.id.new3:
                      new  AlertDialog.Builder(this)
                              .setTitle("确认" )
                              .setMessage("确认退出吗？" )
                              .setPositiveButton("是" , new  DialogInterface.OnClickListener(){
                                  @Override
                                  public void onClick(DialogInterface dialogInterface, int i) {
                                      finish();
                                      System.exit(0);
                                  }
                              } )
                              .setNegativeButton("否" , null)
                              .show();

                      break;
                   }
           return super.onOptionsItemSelected(item);
            }
    }


package kr.co.teada.ex13dialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String[] items=new String[]{"Apple","kiwi","Orange"};
    boolean[] checked=new boolean[]{true,false,true};

    EditText dialogEt;
    TextView dialogTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View view) {
        //AlertDialog 객체 만들기

        //다이얼로그는 직접 생성할 수 없고,
        //다이얼로그를 만들어주는 건축가(Builder)객체에게 의뢰

        //건축가 객체먼저 만들어
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        //건축가에게 다이얼로그의 모양설계 의뢰
        builder.setTitle("다이얼로그");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage("Dialog 테스트 입니다.");

        //버튼은 최대 3개까지 가능하며, 버튼마다 고유 이름 있어
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"OK",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               Toast.makeText(MainActivity.this,"Cancer",Toast.LENGTH_SHORT).show();
            }
        });
        //위에까지는 모두 설계만 한 것

        //건축가에게 다이얼로그 객체 만들어 달라고 요청!! 이제 객체 요청->설계 먼저, 그 다음 객체생성
        AlertDialog dialog=builder.create();

        //다이얼로그의 바깥쪽을 터치했을 때 원래 꺼지는데 이거 안꺼지도록 설정
        //dialog.setCancelable(false); //뒤로가기 눌러도 안 꺼지게 [되게 중요한거 할때만 사용]
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


    }

    public void clickBtn2(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setTitle("목록 다이얼로그");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }

    public void clickBtn3(View view) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("SingleChoice 다이얼로그");

        //두 번째 파라미터: 처음 시작 할 때(다이얼로그가 보일 때) 선택된 Radio Button의 인덱스 번호(1:두 번째 아이템)
        builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,items[which],Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.create().show();
    }

    public void clickBtn4(View view) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Multiple Choice 다이얼로그");

        builder.setMultiChoiceItems(items, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checked[which]=isChecked;
            }
        });
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s="";
                for(int i=0; i<checked.length; i++){
                    s += checked[i];
                }

                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    public void clickBtn5(View view) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("CustomView 다이얼로그");

        //사용자가 원하는 뷰를 만들어서 다이얼로그에 보여주기

        //res 폴더 안에 layout폴더 안에 있는 dialog.xml을
        //뷰 객체로 생성(부풀리다 inflater)시켜주는 객체를 Context(운영체제 대리인)로 부터 얻어와
        LayoutInflater inflater=getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog,null);

        //*****표시 여기야!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 맨 위에 참조변수 만들고!
        //만들어진 layout(LinearLayout 객체)에게
        //안에 있는 EditText와 TextView를 찾아달라고 요청!!
        dialogEt=layout.findViewById(R.id.dialog_et);
        dialogTv=layout.findViewById(R.id.dialog_tv);

        builder.setView(layout);

        builder.setPositiveButton("확인", null); //그냥 끌거면 리스너 대신 null
        builder.create().show();
    }

    //다이얼로그 안에 있는 버튼의 onClick에 반응하는 메소드
    public void clickDialogBtn(View v){

        //MainActivity가 보여주는 activity_main.xml안에
        //"R.id.dialog_et" 아이디 값 없어!!!!!!!!!!!!
        //아래처럼 find를 하면 실행할 때 에러남!
       // EditText et=findViewById(R.id.dialog_et);

        //EditText를 가지고 있는 dialog.xml의 LinearLayout에게
        //EditText를 찾아달라고 할것임~!
        //이 작업은 여기서 하는게 아니라 .. 저 위의 inflate 될 때 해야 돼 //*****표시해둘께

        //tv에 찍은거 보여줘
        dialogTv.setText(dialogEt.getText().toString());

    }
}

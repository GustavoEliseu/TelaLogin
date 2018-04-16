package com.gustavosmd.telalogin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**como é uma tarefa apenas para testes, estou declarando tudo como global e inicializando no onCreate
Gustavo Amaral Eliseu
 matricula: 358003
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //declaração de instancias globais
    User[] usuarios;
    String listaUsuarios="";
    Button loginBtn,limparBtn;
    ToggleButton toggleButton;
    EditText emailEdit,senhaEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            //inicialização
        loginBtn= (Button)findViewById(R.id.loginBtn);
        toggleButton=(ToggleButton) findViewById(R.id.toggleBtnPassword);
        limparBtn=(Button) findViewById(R.id.limparBtn);
        emailEdit=(EditText) findViewById(R.id.emailEdit);
        senhaEdit=(EditText)findViewById(R.id.passwordEdit);
        String[] loginESenhaSalvos = lerLogin();

        //recupera os dados salvos(ou resetados) dos EditText
        emailEdit.setText(loginESenhaSalvos[0]);
        senhaEdit.setText(loginESenhaSalvos[1]);
        //recupera a posição anterior do botão salvar Senha
        toggleButton.setChecked(Boolean.valueOf(loginESenhaSalvos[2]));

        //Iniciando os Listeners.
        loginBtn.setOnClickListener(this);
        limparBtn.setOnClickListener(this);

        //remove os valores de loginESenhaSalvos para facilitar a execução do GCC
        for(int i = 0;i<loginESenhaSalvos.length;i++){
            loginESenhaSalvos[i]=null;
        }
        loginESenhaSalvos=null;



    }

    @Override
    public void onStart(){
        super.onStart();
        usuarios= new User[getResources().getStringArray(R.array.lista_senhas).length];

        //Formas de se salvar/recuperar usuarios utilizando SharedPreferences
        //Deixar apenas uma descomentada

        //Forma 1 - Juntando todos em uma String, separando os valores com characteres especificos, e utilizando Split para recupera-los.

        //verifica se listaUsuários já existe no SharedPreferences, caso não exista este é criado e adicionado.
        /*

        if(getSharedPreferences("prefLogin", Activity.MODE_PRIVATE).getString("listaUsuarios", "").equals("")
                ||getSharedPreferences("prefLogin", Activity.MODE_PRIVATE).getString("listaUsuarios", null)==null){
            for(int i=0;i<usuarios.length;i++){
                listaUsuarios=listaUsuarios+(i+1)+","+getResources().getStringArray(R.array.lista_users)[i]+","
                        +getResources().getStringArray(R.array.lista_senhas)[i]+","
                        +getResources().getStringArray(R.array.lista_nomes)[i]+";";

            }
            salvarUsuarios(listaUsuarios);
        }

        //recupera String listaUsuarios do SharedPreferences, atraves da função lerListaUsuarios()
        String listaDeUsuarios=lerUsuarios();
        usuarios = new User[listaDeUsuarios.split(";").length];

        //Ao recuperar a String, se separa os dados utilizando o metodo "String".Split com o characteres desejados(no caso "," e ";")
        String[] controleTodosUsuarios =listaDeUsuarios.split(";");
        for(int i=0;i<usuarios.length;i++){
            String[] controleUsuarioI= controleTodosUsuarios[i].split(",");
            usuarios[i]=new User(controleUsuarioI[1],controleUsuarioI[2],controleUsuarioI[3]);
        }

        */
        //Fim da Forma 1.



        //Forma 2 - Utilizando Set ou derivados para salvar um array de strings(formando uma pseudo tabela), pode se recuperar os valores de cada usuário separadamente.

        /*

        if(getSharedPreferences("prefLogin", Activity.MODE_PRIVATE).getStringSet("listaDeUsuarios2",null )==null){
        String[] listaDeUsuarios= new String[getResources().getStringArray(R.array.lista_senhas).length];
        for(int i=0;i<usuarios.length;i++){
            listaDeUsuarios[i]=(i+1)+","+getResources().getStringArray(R.array.lista_users)[i]+","
                    +getResources().getStringArray(R.array.lista_senhas)[i]+","
                    +getResources().getStringArray(R.array.lista_nomes)[i];

        }

        salvarUsuarios2(listaDeUsuarios);
        }


        usuarios = new User[getSharedPreferences("prefLogin", Activity.MODE_PRIVATE).getStringSet("listaDeUsuarios2",null ).size()];
        for(int i = 0;i<usuarios.length;i++){
            String UsuarioStr[]=lerUsuarios2(i).split(",");
            usuarios[i]=new User(UsuarioStr[1],UsuarioStr[2],UsuarioStr[3],Integer.parseInt(UsuarioStr[0]));
        }
        Arrays.sort(usuarios);

        */
        //Fim da forma 2



        //Forma 3 - Utilizar Gson para transformar um User[] em uma String.
         /*

         if(getSharedPreferences("prefLogin", Activity.MODE_PRIVATE).getString("listaDeUsuarios3", "").equals("")
                ||getSharedPreferences("prefLogin", Activity.MODE_PRIVATE).getString("listaDeUsuarios3", null)==null){
            usuarios= new User[getResources().getStringArray(R.array.lista_users).length];
            for(int i=0;i<getResources().getStringArray(R.array.lista_senhas).length;i++){
                usuarios[i]=new User(getResources().getStringArray(R.array.lista_users)[i],
                        getResources().getStringArray(R.array.lista_senhas)[i],
                        getResources().getStringArray(R.array.lista_nomes)[i]);
            }
            salvarUsuarios3(usuarios);
        }
        usuarios= lerUsuarios3();

        */
        //Fim da forma 3



        //Forma 4 - Criar Set<String> para cada usuario no shared, com nomes como usuario1, usuario2... e um inteiro com o numero de usuarios.
        if(getSharedPreferences("prefLogin", Activity.MODE_PRIVATE).getStringSet("usuarioNumero"+0,null )==null){
            String[] listaDeUsuarios= new String[getResources().getStringArray(R.array.lista_senhas).length];
            for(int y=0;y<usuarios.length;y++) {
                //Adiciona-se um numero correspondente a ordem de cada string no array, pois a memória ira embaralhar o Set<String>
                listaDeUsuarios = new String[]{"0 "+String.valueOf(y), "1 "+getResources().getStringArray(R.array.lista_users)[y],
                        "2 "+getResources().getStringArray(R.array.lista_senhas)[y],
                        "3 "+getResources().getStringArray(R.array.lista_nomes)[y]};
                salvarUsuarios4(listaDeUsuarios,y);
            }

        }
        String[] UsuarioStr=new String[4];
        for(int i = 0;i<usuarios.length;i++){
            UsuarioStr=lerUsuarios4(i);
            reordenarStrings(UsuarioStr);

            UsuarioStr[0]= UsuarioStr[0].replace("0 ","");
            UsuarioStr[1]= UsuarioStr[1].replace("1 ","");
            UsuarioStr[2]= UsuarioStr[2].replace("2 ","");
            UsuarioStr[3]= UsuarioStr[3].replace("3 ","");

            usuarios[i]=new User(UsuarioStr[1],UsuarioStr[2],UsuarioStr[3],Integer.parseInt(UsuarioStr[0]));
            Log.e("Teste 4: ",UsuarioStr[0]+" "+ UsuarioStr[1]);
        }
        //Arrays.sort(usuarios);

    }

    //Apesar de não implementado, as formas de recuperar dados podem ser usadas ao efetuar o onClick
    @Override
    public void onClick(View v) {


        if(v==loginBtn){
            //loop que compara o fornecido com cada usuário da lista
            for(int i=0;i<usuarios.length;i++){
                //caso email e senha estejam corretos, se fornece o retorno para o usuário e salva caso o toggle esteja ligado.Logo após se sai do loop.
                if(emailEdit.getText().toString().equals(usuarios[i].getLogin()) && senhaEdit.getText().toString().equals(usuarios[i].getSenha())){
                    Toast.makeText(this,"ambos estão corretos!",Toast.LENGTH_SHORT).show();
                    Intent intent;
                    if(toggleButton.isChecked()==true){
                        salvarLogin(emailEdit.getText().toString(),senhaEdit.getText().toString(),toggleButton.isChecked());
                    }else if(toggleButton.isChecked()==false){
                        //retira os dados do sharedPreference caso o toggle esteja vazio;
                        salvarLogin("","",toggleButton.isChecked());
                    }
                    switch(i){
                        case 0:
                            String url= "http://portal.virtual.ufc.br/";
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                            break;
                        case 9:
                            intent = new Intent(this, LogadoActivity.class);
                            intent.putExtra("meuUser",usuarios[i]);
                            startActivity(intent);
                            break;
                    }
                    break;
                }

                //caso o usuário esteja correto e a senha errada, avisa ao usuário e sai do loop.
                else if(emailEdit.getText().toString().equals(usuarios[i].getLogin()) && !(senhaEdit.getText().toString().equals(usuarios[i].getSenha()))){
                    Toast.makeText(this,"senha incorreta!",Toast.LENGTH_SHORT).show();
                    break;
                }

                //Dá retorno ao usuário, caso no fim do loop o usuário não esteja na lista
                else if(i==usuarios.length-1){
                    Toast.makeText(this,"Usuario inexistente",Toast.LENGTH_SHORT).show();
                }
            }

        }else if(v==limparBtn){
            SharedPreferences prefLogin=getSharedPreferences("prefLogin",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor= prefLogin.edit();
            editor.clear();
            editor.commit();
            this.finish();
        }

    }

//Funções do Exercicio 3- salvarLogin() e lerLogin()

    //Função que salva o login,senha e a posição do botão no sharedpreferences
    public void salvarLogin(String login, String senha, boolean isChecked){
        int mode = Activity.MODE_PRIVATE;
        SharedPreferences prefLogin= getSharedPreferences("prefLogin",mode);
        SharedPreferences.Editor editor= prefLogin.edit();
        editor.putString("login",login);
        editor.putString("senha",senha);
        editor.putBoolean("isChecked",isChecked);
        editor.commit();
    }

    //Verifica o login,senha e posição do toggleButton, e retorna como um arrayString[login,senha,toggle(true ou false).
    public String[] lerLogin(){
        int mode= Activity.MODE_PRIVATE; //Quem pode acessar? apenas a aplicação.
        SharedPreferences prefLogin=
                getSharedPreferences("prefLogin", mode);
        String[] loginESenha= new String[]{prefLogin.getString("login", ""),prefLogin.getString("senha", ""),String.valueOf(prefLogin.getBoolean("isChecked",false))};

        return loginESenha;
    }

//Funções do Exercicio 4- salvarLogin() e lerLogin()

    //Forma 1- justar todos em apenas uma String, e utilizar Split para separa-los
    public void salvarUsuarios(String usuarios){
        int mode = Activity.MODE_PRIVATE;
        SharedPreferences prefLogin= getSharedPreferences("prefLogin",mode);
        SharedPreferences.Editor editor= prefLogin.edit();
        editor.putString("listaUsuarios",usuarios);
        editor.commit();
    }

    public String lerUsuarios(){
        int mode= Activity.MODE_PRIVATE; //Quem pode acessar? apenas a aplicação.
        SharedPreferences prefLogin=
                getSharedPreferences("prefLogin", mode);
        String listaUsuarios= prefLogin.getString("listaUsuarios", "");
        return listaUsuarios;
    }

    //Forma 2- Utilizar Set<String> para passar um set de strings para o Shared Preferences
    public void salvarUsuarios2(String[] usuarios){
        HashSet<String> listaUsuarios=new HashSet<String>(Arrays.asList(usuarios));
        int mode = Activity.MODE_PRIVATE;
        SharedPreferences prefLogin= getSharedPreferences("prefLogin",mode);
        SharedPreferences.Editor editor= prefLogin.edit();
        editor.putStringSet("listaDeUsuarios2",listaUsuarios);
        editor.commit();
    }

    public String lerUsuarios2(int posicao){
        int mode= Activity.MODE_PRIVATE;
        String usuario;
        SharedPreferences prefLogin=
                getSharedPreferences("prefLogin", mode);
        HashSet<String> meuHashSet=(HashSet<String>)prefLogin.getStringSet("listaDeUsuarios2", null);
        String[] listaUsuarios= (String[])meuHashSet.toArray(new String[meuHashSet.size()]);
        Log.e("teste lerUser2:", listaUsuarios[posicao].split(",")[1]);
        return listaUsuarios[posicao];
    }

    //Forma 3- Utilizar a biblioteca Gson para passar e recuperar os usuários.
    public void salvarUsuarios3(User[] usuarios){
        Gson gson = new Gson();
        String jsonInString = gson.toJson(usuarios);
        int mode = Activity.MODE_PRIVATE;
        SharedPreferences prefLogin= getSharedPreferences("prefLogin",mode);
        SharedPreferences.Editor editor= prefLogin.edit();
        editor.putString("listaDeUsuarios3",jsonInString);
        editor.commit();
    }

    public User[] lerUsuarios3(){
        Gson gson = new Gson();
        int mode= Activity.MODE_PRIVATE; //Quem pode acessar? apenas a aplicação.
        SharedPreferences prefLogin=
                getSharedPreferences("prefLogin", mode);
        String listaUsuarios= prefLogin.getString("listaDeUsuarios3", "");
        User usuarios[] = gson.fromJson(listaUsuarios, User[].class);

        return usuarios;
    }

    //Forma 4- Utilizar um Set<String> para cada usuario e um Int para quantidade de usuarios.
    public void salvarUsuarios4(String[] usuarioX, int posicao){
        HashSet<String> listaUsuarios=new HashSet<String>(Arrays.asList(usuarioX));
        int mode = Activity.MODE_PRIVATE;
        SharedPreferences prefLogin= getSharedPreferences("prefLogin",mode);
        SharedPreferences.Editor editor= prefLogin.edit();
        editor.putStringSet("usuarioNumero"+posicao,listaUsuarios);
        editor.commit();
    }

    public String[] lerUsuarios4(int posicao){
        int mode= Activity.MODE_PRIVATE;
        SharedPreferences prefLogin=
                getSharedPreferences("prefLogin", mode);
        String key= "usuarioNumero"+posicao;
        HashSet<String> meuHashSet=(HashSet<String>)prefLogin.getStringSet(key, null);
        String[] listaDoUsuario= (String[])meuHashSet.toArray(new String[meuHashSet.size()]);
        return listaDoUsuario;
    }

    public static void reordenarStrings(String[] minhaString)
    {
        Arrays.sort(minhaString);
    }

}

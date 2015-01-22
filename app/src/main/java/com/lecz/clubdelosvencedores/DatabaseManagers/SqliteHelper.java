package com.lecz.clubdelosvencedores.DatabaseManagers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lecz.clubdelosvencedores.R;

public class SqliteHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "club_vencedores.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATATABLE_USER = "create table User ( id integer primary key autoincrement, "
            + "name text not null, "
            + "age numeric not null, "
            + "genre numeric not null,"
            + "days_without_smoking numeric not null,"
            + "days_without_smoking_count numeric not null, "
            + "plan_type numeric not null, "
            + "cigarettes_no_smoked numeric not null, "
            + "money_saved numeric not null, "
            + "smoking numeric not null, "
            + "cigarettes_day numeric not null, "
            + "last_cigarette numeric not null, "
            + "days_with_smoking numeric not null, "
            + "years_smoking numeric not null"
            + ");";

    private static final String DATATABLE_PLAN_DETAIL = "create table PlanDetail ( id integer primary key autoincrement, "
            + "number_day numeric not null, "
            + "total_cigarettes numeric not null, "
            + "used_cigarettes numeric not null,"
            + "approved numeric not null,"
            + "current numeric not null,"
            + "date numeric not null,"
            + "completed numeric not null"
            + ");";

    private static final String DATATABLE_ACHIEVEMENTS = "create table Achievement ( id integer primary key autoincrement, "
            + "title text not null, "
            + "type text not null, "
            + "amount numeric not null, "
            + "completed numeric not null,"
            + "image numeric not null,"
            + "description text not null"
            + ");";

    private static final String DATATABLE_CONTACTFRIEND = "create table Contact ( id integer primary key autoincrement, "
            + "contact_id numeric not null, "
            + "name text not null, "
            + "phone_number text not null"
            + ");";

    private static final String DATATABLE_NOTICE = "create table Notice ( id integer primary key autoincrement, "
            + "title text not null, "
            + "content text not null, "
            + "summary text not null, "
            + "link text not null, "
            + "url text not null, "
            + "image blob null, "
            + "date numeric not null"
            + ");";

    private static final String DATATABLE_ADVICE = "create table Advice ( id integer primary key autoincrement, "
            + "type text not null, "
            + "body text not null, "
            + "cat_genre numeric not null, "
            + "motiv_money numeric not null, "
            + "motiv_aesthetic numeric not null, "
            + "motiv_family numeric not null, "
            + "motiv_health numeric not null"
            + ");";

    private static final String DATATABLE_MOTIVATIONS = "create table Motivations ( id integer primary key autoincrement, "
            + "motiv_money numeric not null, "
            + "motiv_aesthetic numeric not null, "
            + "motiv_family numeric not null, "
            + "motiv_health numeric not null"
            + ");";

    private static final String DATATABLE_ACTIVITYLOG = "create table Activity ( id integer primary key autoincrement, "
            + "title text not null, "
            + "content text not null, "
            + "type text not null, "
            + "image numeric not null, "
            + "date numeric not null"
            + ");";

    private static final String DATATABLE_7DAYS = "create table SevenDayPlan ( id integer primary key autoincrement, "
            + "number_cigarettes text not null, "
            + "day1 numeric not null, "
            + "day2 numeric not null, "
            + "day4 numeric not null, "
            + "day3 numeric not null, "
            + "day5 numeric not null,"
            + "day6 numeric not null,"
            + "day7 numeric not null"
            + ");";

    private static final String DATATABLE_15DAYS = "create table fifteenDaysPlan ( id integer primary key autoincrement, "
            + "number_cigarettes text not null, "
            + "day1 numeric not null, "
            + "day2 numeric not null, "
            + "day3 numeric not null, "
            + "day4 numeric not null, "
            + "day5 numeric not null, "
            + "day6 numeric not null, "
            + "day7 numeric not null, "
            + "day8 numeric not null, "
            + "day9 numeric not null, "
            + "day10 numeric not null, "
            + "day11 numeric not null, "
            + "day12 numeric not null, "
            + "day13 numeric not null, "
            + "day14 numeric not null, "
            + "day15 numeric not null"
            + ");";

    private static final String DATATABLE_30DAYS = "create table thirtyDaysPlan ( id integer primary key autoincrement, "
            + "number_cigarettes text not null, "
            + "day1 numeric not null, "
            + "day2 numeric not null, "
            + "day3 numeric not null, "
            + "day4 numeric not null, "
            + "day5 numeric not null, "
            + "day6 numeric not null, "
            + "day7 numeric not null, "
            + "day8 numeric not null, "
            + "day9 numeric not null, "
            + "day10 numeric not null, "
            + "day11 numeric not null, "
            + "day12 numeric not null, "
            + "day13 numeric not null, "
            + "day14 numeric not null, "
            + "day15 numeric not null, "
            + "day16 numeric not null, "
            + "day17 numeric not null, "
            + "day18 numeric not null, "
            + "day19 numeric not null, "
            + "day20 numeric not null, "
            + "day21 numeric not null, "
            + "day22 numeric not null, "
            + "day23 numeric not null, "
            + "day24 numeric not null, "
            + "day25 numeric not null, "
            + "day26 numeric not null, "
            + "day27 numeric not null, "
            + "day28 numeric not null, "
            + "day29 numeric not null, "
            + "day30 numeric not null"
            + ");";

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATATABLE_USER);
        database.execSQL(DATATABLE_PLAN_DETAIL);
        database.execSQL(DATATABLE_ACHIEVEMENTS);
        database.execSQL(DATATABLE_CONTACTFRIEND);
        database.execSQL(DATATABLE_NOTICE);
        database.execSQL(DATATABLE_ADVICE);
        database.execSQL(DATATABLE_MOTIVATIONS);
        database.execSQL(DATATABLE_ACTIVITYLOG);
        database.execSQL(DATATABLE_7DAYS);
        database.execSQL(DATATABLE_15DAYS);
        database.execSQL(DATATABLE_30DAYS);

        createAchievements(database);
        createAdvices(database);
        createPlansConfig(database);
    }

    private void createAchievements(SQLiteDatabase database){
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( '20 minutos sin fumar', 'time', " + 20 + ", " + 0 + ", " + R.drawable.icn_01 + ", " + "'Tu presión arterial y ritmo cardíaco se normalizan. Lo podés sentir en la temperatura de tus pies y tus manos.');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'Ocho horas sin fumar', 'time', " + 60 * 8 + ", " + 0 + ", " + R.drawable.icn_02 + ", " + "'Los niveles de monóxido de carbono en la sangre se reducen a la mitad y los niveles de oxígeno aumentan a valores normales.');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'Un día sin fumar', 'time', " + 60 * 24 * 1 + ", " + 0 + ", " + R.drawable.icn_03 + ", " + "'Disminuye el riesgo de infarto de miocardio y tus pulmones empiezan a limpiarse. Pronto estarás respirando mucho mejor');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'Dos días sin fumar', 'time', " + 60 * 24 * 2 + ", " + 0 + ", " + R.drawable.icn_04 + ", " + "'Hmmm... ¿No te sabe más rica la comida? Empezás a recuperar tu capacidad para oler y saborear. ¡Provecho!');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'Tres días sin fumar', 'time', " + 60 * 24 * 3 + ", " + 0 + ", " + R.drawable.icn_05 + ", " + "'Tu capacidad pulmonar aumenta y ya no tosés tan frecuentemente. Se siente bien, ¿verdad?');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( '15 días sin fumar', 'time', " + 60 * 24 * 15 + ", " + 0 + ", " + R.drawable.icn_06 + ", " + "'La dependencia física a la nicotina desaparece. Mantené mentalidad de vencedor y no te permitás recaer. ¡Vos podés!');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'Un mes sin fumar', 'time', " + 60 * 24 * 30 + ", " + 0 + ", " + R.drawable.icn_07 + ", " + "'Tu capacidad física ha mejorado y ya no te cansás tan fácil. Empezá poco a poco a hacer ejercicio; ¡te sentirás aun mejor!');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'Tres meses sin fumar', 'time', " + 60 * 24 * 30 * 3 + ", " + 0 + ", " + R.drawable.icn_08 + ", " + "'Aumenta tu capacidad para resistir a las infecciones. ¡Adiós a la gripe!');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'Un año sin fumar', 'time', " + 60 * 24 * 30 * 12 + ", " + 0 + ", " + R.drawable.icn_09 + ", " + "'Tu riesgo de sufrir un infarto se ha reducido a la mitad.¡Feliz aniversario vencedor!');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'Has ahorrado 5000 colones, eso equivale a tu postre favorito', 'money', " + 5000 + ", " + 0 + ", " + R.drawable.icn_10 + ", " + "'Algunos alimentos, como la naranja y la piña, pueden ayudarte a controlar la ansiedad que provoca el dejar de fumar. ¡Además son deliciosos!');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'Has ahorrado 10000 colones, eso equivale a entrada doble al cine (palomitas incluidas)', 'money', " + 10000 + ", " + 0 + ", " + R.drawable.icn_11 + ", " + "'El pasar el rato en lugares donde sea prohibido fumar te puede ayudar cuando sintás que tu control flaquea.');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'Has ahorrado 25000 colones, eso equivale a membresía en gimnasio', 'money', " + 25000 + ", " + 0 + ", " + R.drawable.icn_12 + ", " + "'Sacale el jugo a todo ese aire que tenés ahora y cumplí tu meta de ponerte en forma.');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'Has ahorrado 50000 colones, eso equivale a cena en familia', 'money', " + 50000 + ", " + 0 + ", " + R.drawable.icn_13 + ", " + "'Agradeceles a los tuyos por todo ese apoyo que te han brindado durante este proceso. ¡Una cena especial en su restaurante favorito seguro que les encantará!');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'Has ahorrado 75000 colones, eso equivale a una tablet', 'money', " + 75000 + ", " + 0 + ", " + R.drawable.icn_14 + ", " + "'Ponte tecnológico y disfrutá de todas las posibilidades que uno de estos dispositivos te puede ofrecer.');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'Has ahorrado 150000 colones, eso equivale a un fin de semana en la playa', 'money', " + 150000 + ", " + 0 + ", " + R.drawable.icn_15 + ", " + "'Te has esforzado mucho por llegar hasta aquí. ¿No te suena un poco de sol, mar y arena?');");
        database.execSQL("INSERT INTO Achievement ('title', 'type', 'amount', 'completed', 'image', 'description') values ( 'Has ahorrado 200000 colones, eso equivale a pantalla plana', 'money', " + 200000 + ", " + 0 + ", " + R.drawable.icn_16 + ", " + "'¡Perfecta para gritar a todo pulmón durante los partidos de la sele!');");

    }

    private void createAdvices(SQLiteDatabase database){

        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'La nicotina debilita las paredes de tu estómago, lo que puede causar úlceras.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Las toxinas presentes en el cigarrillo disminuyen la concentración y movilidad de los espermatozoides en el semen.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'El consumo de cigarrillo es la causa de 11 de cada 12 personas que contraen cáncer de pulmón.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Al inhalar humo de tabaco, impedís que tu organismo reciba el oxígeno necesario para funcionar adecuadamente.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'El tabaco produce una sustancia llamada carboxihemoglobina que impide la conducción de la sangre.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Al fumar, tu ritmo cardíaco se acelera de 5 a 20 latidos por minuto, pero la nicotina hace que tus vasos sanguíneos se contraigan y la sangre fluye con dificultad.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'De los químicos presentes en el cigarrillo, al menos 200 son venenos conocidos y 60, cancerígenos.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Si fumás, tenés más probabilidades de sufrir un infarto debido a que tu corazón necesita hacer un mayor esfuerzo para bombear la sangre.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Puede que al dejar de fumar, experimentés una serie de síntomas como irritabilidad, temblores, sudoración o tos excesiva. Estos son normales y con el tiempo desaparecerán.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'La nicotina es el compuesto responsable de producir la dependencia física y psicológica a los cigarrillos. Esta es 7 veces más adictiva que la heroína. ', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Cuando dejás de fumar te sentís mal porque tu cuerpo se ha acostumbrado a recibir una dosis diaria de nicotina, pero vos podés contra ello. ¡No te rindás!', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Sin tabaco, te olvidás de los ataques de tos matutinos, la congestión nasal y la sensasión de ahogo.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Los fumadores pasivos tienen entre un 25% y un 30% más de riesgo de sufrir un infarto.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'La exposición al humo del tabaco puede provocar enfisema y EPOC (enfermedad pulmonar obstructiva crónica) en adultos no fumadores.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Al dejar el tabaco, mejora tu circulación y tu capacidad pulmonar. Vas a tener más aire para hacer toda la actividad física que querás.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'A los años de dejar de fumar, se disminuirá tu riesgo de padecer cáncer y, eventualmente, este será igual que el un no fumador.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Los hijos de fumadoras tienen mayores probabilidades de padecer parálisis cerebral, retraso mental, disfunciones de aprendizaje y problemas de comportamiento.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Comé muchas frutas y vegetales, estos fortalecen tus defensas y ayudan a reparar los daños provocados por el tabaco..', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Tu salud es siempre lo primero. Un ligero aumento de peso no se compara en nada con los perjuicios que trae a tu salud el seguir fumando.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Evitá el consumo de bebidas alcohólicas. Muchas personas asocian su consumo con fumar, lo que las convierte en una tentación que es mejor evitar.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Sacá provecho de haber recuperado tu sentido del gusto. Saboreá tus alimentos y aprendé a comer más despacio.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Al fumar, inhalás monóxido de carbono, este es un gas tóxico que interfiere en el transporte del oxígeno por la sangre y aumenta el riesgo de padecer enfermedades cardiovasculares.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'El alquitrán es uno de los compuestos presentes en los cigarrillos, es el responsable de la mayoría de las lesiones pulmonares y tiene la capacidad de estimular el desarrollo de células tumorales.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'En cada cigarrillo podés encontrar al menos 40 tipos distintos de sustancias cancerígenas. Entre ellas el benzopireno, el benceno y la dimetilnitrosamina.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Las sustancias irritantes presentes en el humo de tabaco lesionan la mucosa respiratoria, provocando lo que se conoce como “tos del fumador”, bronquitis y, a largo plazo, enfisema.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Abrazá y besá a tus seres queridos sin tener que preocuparte por el olor a humo. ', " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'No te perdás de más momentos en familia por estar saliendo a fumar constantemente.', " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'El humo de tabaco es altamente nocivo para todos los que lo inhalan. Protegé a tu familia, brindales un ambiente limpio en el cual desarrollarse.', " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Vos tenés derecho a disfrutar de un ambiente libre de humo de tabaco, hacé valer tus derechos por tu salud y la de tu familia.', " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'En el mundo, casi la mitad de l@s niñ@s están expuestos al humo de tabaco. En el 2004, representaron un 31% de las muertes atribuibles al humo ajeno.', " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'No hay ventana o ventilador que pueda eliminar efectivamente el humo de tabaco del ambiente, este puede permanecer por días e incluso semanas.', " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'En l@s niñ@s, el humo de segunda mano aumenta el riesgo de infecciones respiratorias, crisis asmáticas, neumonía y bronquitis', " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Hablá con tu familia y amigos y pediles su apoyo y comprensión durante este proceso.', " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Durante el embarazo, la exposición al humo de tabaco reduce el suministro de sangre a la placenta, lo que afecta el desarrollo del feto y le hace más vulnerable a sufrir de enfermedades.', " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Los bebés de madres fumadoras pesan en promedio 200 gramos menos.', " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'En los bebés expuestos al humo de tabaco el riesgo de morir antes del parto o de sufrir el síndrome muerte súbita del lactante es mayor.', " + 0 + ", " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Los cigarrillos son costosos. Calculá todo el dinero que estás ahorrando y decidí en qué lo vas a gastar. Date un gusto.', " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Con el dinero que ahorrás al no comprar cigarros, te podés premiar y consentirte comprando lo que más te guste.', " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Si dejás de fumar 1 cajetilla de cigarrillos al día, ahorrarás 540.000 colones en un solo año.', " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Cuando fumás el olor del tabaco se adhiere a la mucosa de tu boca, lengua y dientes y puede generar mal aliento.', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'En los hombres, fumar debilita el flujo de sangre al pene y duplica las probabilidades de disfunción eréctil.', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'En las mujeres, fumar provoca una disminución del interés sexual.', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Los fumadores tienen el cabello más frágil y son más propensos a la calvicie.', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'La nicotina hace que las uñas de tus manos y pies adquieran un color amarillento.', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Los compuestos químicos presentes en los cigarrillos debilitan la circulación sanguínea en tu piel, lo que aumenta el riesgo de espinillas y otros tipos de acné.', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'El tabaco promueve la pérdida de elasticidad en la piel y favorece la aparición de arrugas.', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'El alquitrán en los cigarrillos es el responsable de las manchas que pueden aparecer en tus dientes y del mal aliento característico de los fumadores.', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Los fumadores acumulan grasa adicional en el abdomen y tienden a padecer de sobrepeso.', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'La nicotina, sumada a un estilo de vida sedentario, promueve la aparición de celulitis.', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Fumar altera tu voz, debido a que afecta la laringe, donde se alojan las cuerdas vocales.', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'En las mujeres, fumar produce un desequilibrio hormonal, propiciando así la aparición de vello corporal y facial no deseado.', " + 0 + ", " + 0 + ", " + 1 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Podés equivocarte, cometer deslices y tener recaídas, pero sos un vencedor y los vencedores nunca se rinden.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'No importa tu edad, ni los años que tengás de fumar, nunca es demasiado tarde para dejar el tabaco.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Sos un vencedor porque decidiste decir NO. Seguí adelante.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Al dejar de fumar, podés llegar a experimentar episodios de ansiedad y nerviosismo, pero no te preocupés, estos son pasajeros.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'La nicotina presente en los cigarrillos es un estimulante, por lo que es falso afirmar que fumar ayuda a controlar el estrés.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Todo intento para dejar de fumar es una oportunidad para aprender y te deja un poco más cerca de tu meta.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Cuando dejás de fumar, experimentás una sensación de mayor bienestar y libertad. Te sentís más en control de tu vida.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'Cuando te exponés a un ambiente contaminado con humo de tabaco, sos un fumador pasivo y, por tanto, vulnerable a los mismo riesgos que si fumaras. ', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'No existe un nivel de contaminación por humo de tabaco que resulte seguro, los espacios 100% libres de humo ofrecen la única protección.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Cuando creás que necesitás fumar, dejá inmediatamente lo que estás haciendo. Un simple cambio de ambiente puede ayudarte a combatir la ansiedad.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Mantené las manos ocupadas, comé maní, nueces o algún otro bocadillo, preferiblemente bajo en azúcar.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Llamá o enviá un mensaje a un amigo o un familiar. No tenés que hacer esto solo.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Tomate el proceso de dejar de fumar, un día a la vez.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Si lo necesitarás, no dudes en llamar a nuestra línea de orientación gratuita 800-IAFA-800 (800-4232-800)', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Tomá mucha agua. El agua proporciona una sensación de saciedad, te hace comer menos y ayuda a desintoxicar tu cuerpo.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Practicá algún tipo de actividad física, de esta forma te distraerás y controlarás mejor los cambios de peso y de humor que podés enfrentar al dejar de fumar.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Cuando te sintás estresado o nervioso, respirá profundamente. Inhala hondo por la nariz y exhalá lentamente por la boca. Repetilo 10 veces.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Si al dejar de fumar te sentís irritable o de mal humor, hacé ejercicio. Así liberás endorfinas y te sentirás más tranquilo y relajado. ', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Meditá en un ambiente tranquilo durante unos minutos cada día. Tendrás mejor desempeño físico y mental y enfrentar mejor las crisis sin recurrir al tabaco.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Pensá siempre en positivo. Incluso cuando cometés algún desliz. Vos sos un vencedor, por tanto, podés hacer frente a este proceso.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'En momentos de crisis, recordá todos los beneficios que conlleva el dejar de fumar. ', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Escribí una lista con tus motivaciones para dejar de fumar y reléela a menudo.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Alrededor del mundo, más de la mitad de las personas fumadoras han logrado dejar de fumar. Siendo así, ¿por qué no vas a poder vos también?', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Quizás dejar de fumar no sea fácil y quizás no lo consigás a la primera, pero si perseverás y mantenés una actitud positiva, vas a llegar a vencer.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'No hay tal cosa como un momento perfecto para dejar de fumar. Cuanto antes mejor... ¿Qué te parece... ya?', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Tips para Vencedores', 'Sacá provecho de haber recuperado tu sentido del gusto. Saboreá tus alimentos y aprendé a comer más despacio.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
        database.execSQL("INSERT INTO Advice ('type', 'body', 'cat_genre', 'motiv_money', 'motiv_aesthetic', 'motiv_family', 'motiv_health') values ('Sabías que...', 'La Ley 9028, se creó para protegerte a vos y a las demás personas de la exposición al humo de tabaco en espacios públicos. Exigí que se respete.', " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ");");
    }

    private void createPlansConfig(SQLiteDatabase database){
        database.execSQL("INSERT INTO SevenDayPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7') values ( '4', 3, 3, 2, 2, 1, 1, 1);");
        database.execSQL("INSERT INTO SevenDayPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7') values ( '5', 4, 3, 3, 2, 2, 1, 1);");
        database.execSQL("INSERT INTO SevenDayPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7') values ( '6', 4, 3, 3, 2, 2, 1, 1);");
        database.execSQL("INSERT INTO SevenDayPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7') values ( '7', 6, 5, 4, 3, 2, 1, 1);");
        database.execSQL("INSERT INTO SevenDayPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7') values ( '8', 6, 4, 4, 2, 2, 1, 1);");
        database.execSQL("INSERT INTO SevenDayPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7') values ( '9', 6, 4, 4, 2, 2, 1, 1);");
        database.execSQL("INSERT INTO SevenDayPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7') values ( '10-15', 8, 6, 4, 4, 2, 2, 1);");
        database.execSQL("INSERT INTO SevenDayPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7') values ( '16-20', 13, 10, 7, 5, 3, 2, 1);");
        database.execSQL("INSERT INTO SevenDayPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7') values ( '21-30', 17, 13, 9, 5, 3, 2, 1);");
        database.execSQL("INSERT INTO SevenDayPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7') values ( '31-40', 25, 19, 13, 7, 5, 3, 1);");
        database.execSQL("INSERT INTO SevenDayPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7') values ( '41-50', 34, 27, 20, 13, 5, 3, 1);");
        database.execSQL("INSERT INTO SevenDayPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7') values ( '50-more', 41, 32, 23, 14, 5, 3, 1);");

        database.execSQL("INSERT INTO fifteenDaysPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7', 'day8', 'day9', 'day10', 'day11', 'day12', 'day13', 'day14', 'day15') values ( '8', 7, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 2, 2, 1, 1);");
        database.execSQL("INSERT INTO fifteenDaysPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7', 'day8', 'day9', 'day10', 'day11', 'day12', 'day13', 'day14', 'day15') values ( '9', 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1);");
        database.execSQL("INSERT INTO fifteenDaysPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7', 'day8', 'day9', 'day10', 'day11', 'day12', 'day13', 'day14', 'day15') values ( '10-15', 9, 8, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1);");
        database.execSQL("INSERT INTO fifteenDaysPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7', 'day8', 'day9', 'day10', 'day11', 'day12', 'day13', 'day14', 'day15') values ( '16-20', 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);");
        database.execSQL("INSERT INTO fifteenDaysPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7', 'day8', 'day9', 'day10', 'day11', 'day12', 'day13', 'day14', 'day15') values ( '21-30', 19, 18, 16, 15, 13, 12, 10, 9, 7, 6, 5, 4, 3, 2, 1);");
        database.execSQL("INSERT INTO fifteenDaysPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7', 'day8', 'day9', 'day10', 'day11', 'day12', 'day13', 'day14', 'day15') values ( '31-40', 28, 25, 24, 22, 20, 18, 16, 14, 12, 10, 8, 6, 4, 2, 1);");
        database.execSQL("INSERT INTO fifteenDaysPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7', 'day8', 'day9', 'day10', 'day11', 'day12', 'day13', 'day14', 'day15') values ( '41-50', 38, 35, 32, 29, 26, 23, 20, 17, 14, 11, 8, 6, 4, 2, 1);");
        database.execSQL("INSERT INTO fifteenDaysPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7', 'day8', 'day9', 'day10', 'day11', 'day12', 'day13', 'day14', 'day15') values ( '50-more', 47, 44, 40, 36, 32, 28, 24, 20, 16, 12, 8, 6, 4, 2, 1);");

        database.execSQL("INSERT INTO thirtyDaysPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7', 'day8', 'day9', 'day10', 'day11', 'day12', 'day13', 'day14', 'day15', 'day16', 'day17', 'day18', 'day19', 'day20', 'day21', 'day22', 'day23', 'day24', 'day25', 'day26', 'day27', 'day28', 'day29', 'day30') values ( '16-20', 15, 15, 14, 14, 13, 13, 12, 12, 11, 11, 10, 10, 9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1);");
        database.execSQL("INSERT INTO thirtyDaysPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7', 'day8', 'day9', 'day10', 'day11', 'day12', 'day13', 'day14', 'day15', 'day16', 'day17', 'day18', 'day19', 'day20', 'day21', 'day22', 'day23', 'day24', 'day25', 'day26', 'day27', 'day28', 'day29', 'day30') values ( '21-30', 19, 19, 17, 17, 15, 15, 13, 13, 12, 12, 10, 10, 9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1);");
        database.execSQL("INSERT INTO thirtyDaysPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7', 'day8', 'day9', 'day10', 'day11', 'day12', 'day13', 'day14', 'day15', 'day16', 'day17', 'day18', 'day19', 'day20', 'day21', 'day22', 'day23', 'day24', 'day25', 'day26', 'day27', 'day28', 'day29', 'day30') values ( '31-40', 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);");
        database.execSQL("INSERT INTO thirtyDaysPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7', 'day8', 'day9', 'day10', 'day11', 'day12', 'day13', 'day14', 'day15', 'day16', 'day17', 'day18', 'day19', 'day20', 'day21', 'day22', 'day23', 'day24', 'day25', 'day26', 'day27', 'day28', 'day29', 'day30') values ( '41-50', 39, 38, 36, 35, 33, 32, 30, 29, 27, 26, 24, 23, 21, 20, 18, 17, 15, 14, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);");
        database.execSQL("INSERT INTO thirtyDaysPlan ('number_cigarettes', 'day1', 'day2', 'day3', 'day4', 'day5', 'day6', 'day7', 'day8', 'day9', 'day10', 'day11', 'day12', 'day13', 'day14', 'day15', 'day16', 'day17', 'day18', 'day19', 'day20', 'day21', 'day22', 'day23', 'day24', 'day25', 'day26', 'day27', 'day28', 'day29', 'day30') values ( '50-more', 49, 47, 45, 43, 41, 39, 37, 35, 33, 31, 29, 27, 25, 23, 21, 19, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SqliteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS PlanDetail");
        db.execSQL("DROP TABLE IF EXISTS Achievement");
        db.execSQL("DROP TABLE IF EXISTS Contact");
        db.execSQL("DROP TABLE IF EXISTS Notice");
        db.execSQL("DROP TABLE IF EXISTS Advice");
        db.execSQL("DROP TABLE IF EXISTS Motivations");
        db.execSQL("DROP TABLE IF EXISTS Activity");
        db.execSQL("DROP TABLE IF EXISTS SevenDayPlan");
        db.execSQL("DROP TABLE IF EXISTS fifteenDaysPlan");
        db.execSQL("DROP TABLE IF EXISTS thirtyDaysPlan");
        onCreate(db);
    }

}
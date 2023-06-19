package dev.mah.nassa.gradu_ptojects.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.MVVM.My_Meal_MVVM;
import dev.mah.nassa.gradu_ptojects.Modles.Exercise_Details;
import dev.mah.nassa.gradu_ptojects.Modles.My_Meal_List;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.ViewHolder.ExerciseDetails_ViewHolder;

public class ExerciseDetails_Adapter extends RecyclerView.Adapter<ExerciseDetails_ViewHolder> {

    private int layout;
    private List<Exercise_Details> detailsList;
    private Context context;
    private boolean dropDown=false;
    Activity activity;
    Toolbar toolbar;
    My_Meal_MVVM mainViewModel;
    boolean isEnable=false;
    boolean isSelectAll=false;
    ArrayList<Exercise_Details> selectList=new ArrayList<>();

    public ExerciseDetails_Adapter(int layout, List<Exercise_Details> detailsList, Context context) {
        this.layout = layout;
        this.detailsList = detailsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExerciseDetails_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view =  LayoutInflater.from(context).inflate(layout , parent , false);
      ExerciseDetails_ViewHolder exerciseDetails_viewHolder = new ExerciseDetails_ViewHolder(view);
        // initialize view Model
        mainViewModel= ViewModelProviders.of((FragmentActivity) context)
                .get(My_Meal_MVVM.class);
        return exerciseDetails_viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseDetails_ViewHolder holder, int position) {


                holder.exerciseDuration.setText(detailsList.get(position).getExerciseDuration() + "ساعة");
                holder.nameExercise.setText(detailsList.get(position).getExerciseName() );
                holder.time.setText(detailsList.get(position).getExerciseTime() );
                holder.dateTv.setText(detailsList.get(position).getDate() );
                holder.date.setText(detailsList.get(position).getDate() );
                holder.calories.setText(String.format("%.2f", Double.parseDouble(detailsList.get(position).getCaloriesBurned())) + "سعرة حرارية");

                ViewGroup.LayoutParams layoutParams =  holder.tableLayout.getLayoutParams();
                if((position+1) != detailsList.size()){
                    layoutParams.height=0;
                    holder.tableLayout.setLayoutParams(layoutParams);
                    holder.iconDrop.setRotation(360);
                }else {
                    layoutParams.height=-2;
                    holder.tableLayout.setLayoutParams(layoutParams);
                    holder.iconDrop.setRotation(270);
                }

                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dropDown==false){
                            layoutParams.height=0;
                            holder.iconDrop.setRotation(360);
                            holder.tableLayout.setLayoutParams(layoutParams);
                            dropDown=true;
                        }else {
                            dropDown=false;
                            layoutParams.height=-2;
                            holder.iconDrop.setRotation(270);
                            holder.tableLayout.setLayoutParams(layoutParams);

                        }
                    }
                });

                //عند ضغط ضغطة مطولة
                 holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // check condition
                if (!isEnable)
                {
                    // when action mode is not enable
                    // initialize action mode
                    ActionMode.Callback callback=new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            toolbar.setVisibility(View.GONE);
                            // initialize menu inflater
                            MenuInflater menuInflater= mode.getMenuInflater();
                            // inflate menu
                            menuInflater.inflate(R.menu.click_menu,menu);
                            // return true

                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            // when action mode is prepare
                            // set isEnable true
                            isEnable=true;
                            // create method
                            ClickItem(holder);
                            // set observer on getText method
                            mainViewModel.getTitle().observe((LifecycleOwner) activity
                                    , new Observer<String>() {
                                        @Override
                                        public void onChanged(String s) {
                                            // when text change
                                            // set text on action mode title
                                            mode.setTitle(String.format("%s Selected",s));

                                        }
                                    });
                            // return true
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            // when click on action mode item
                            // get item  id
                            int id=item.getItemId();
                            // use switch condition
                            switch(id)
                            {
                                case R.id.menu_delete:
                                    // when click on delete
                                    // use for loop
                                    for(Exercise_Details s:selectList)
                                    {
                                        // remove selected item list
                                        FireStore_DataBase.deleteExerciseDetails(s);
                                        detailsList.remove(s);
                                    }
                                    // check condition
                                    if(detailsList.size()==0)
                                    {
                                        // when array list is empty
                                        // visible text view
                                        //tvEmpty.setVisibility(View.VISIBLE);
                                    }
                                    // finish action mode
                                    mode.finish();
                                    break;

                                case R.id.menu_select_all:
                                    // when click on select all
                                    // check condition
                                    if(selectList.size()==detailsList.size())
                                    {
                                        // when all item selected
                                        // set isselectall false
                                        isSelectAll=false;
                                        // create select array list
                                        selectList.clear();
                                    }
                                    else
                                    {
                                        // when  all item unselected
                                        // set isSelectALL true
                                        isSelectAll=true;
                                        // clear select array list
                                        selectList.clear();
                                        // add value in select array list
                                        selectList.addAll(detailsList);
                                    }
                                    // set text on view model
                                    mainViewModel.setTitle(String .valueOf(selectList.size()));
                                    // notify adapter
                                    notifyDataSetChanged();
                                    break;
                            }
                            // return true
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            toolbar.setVisibility(View.VISIBLE);
                            // when action mode is destroy
                            // set isEnable false
                            isEnable=false;
                            // set isSelectAll false
                            isSelectAll=false;
                            // clear select array list
                            selectList.clear();
                            // notify adapter
                            notifyDataSetChanged();
                        }
                    };
                    // start action mode
                    ((AppCompatActivity) v.getContext()).startActionMode(callback);
                }
                else
                {
                    // when action mode is already enable
                    // call method
                    ClickItem(holder);
                }
                // return true
                return true;
            }
        });
                 holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check condition
                if(isEnable)
                {
                    // when action mode is enable
                    // call method
                    ClickItem(holder);
                }
                else
                {
                    // when action mode is not enable
                    // display toast
                    Toast.makeText(activity,"You Clicked"+detailsList.get(holder.getAdapterPosition()),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
                 // check condition
                 if(isSelectAll)
                  {
                      // when value selected
                      // visible all check boc image
                      holder.ivCheckBox.setVisibility(View.VISIBLE);
                      //set background color
                      holder.itemView.setBackgroundColor(Color.LTGRAY);
                  }
                   else
                    {
                        // when all value unselected
                        // hide all check box image
                        holder.ivCheckBox.setVisibility(View.GONE);
                        // set background color
                        holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                    }
    }
    private void ClickItem(ExerciseDetails_ViewHolder holder) {

        // get selected item value
        Exercise_Details s=detailsList.get(holder.getAdapterPosition());
        // check condition
        if(holder.ivCheckBox.getVisibility()==View.GONE)
        {
            // when item not selected
            // visible check box image
            holder.ivCheckBox.setVisibility(View.VISIBLE);
            // set background color
            holder.itemView.setBackgroundColor(Color.LTGRAY);
            // add value in select array list
            selectList.add(s);
        }
        else
        {
            // when item selected
            // hide check box image
            holder.ivCheckBox.setVisibility(View.GONE);
            // set background color
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            // remove value from select arrayList
            selectList.remove(s);

        }
        // set text on view model
        mainViewModel.setTitle(String.valueOf(selectList.size()));
    }


    @Override
    public int getItemCount() {
        return detailsList.size();
    }
}



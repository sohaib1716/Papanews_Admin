package darren.gcptts.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SharedMemory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

import darren.gcptts.R;

import static android.content.Context.MODE_PRIVATE;


public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<PersonUtils> personUtils;
    Context mContext;
    String goIntent;

    public CustomRecyclerAdapter(Context context, List personUtils) {
        this.context = context;
        this.personUtils = personUtils;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(personUtils.get(position));
        PersonUtils pu = personUtils.get(position);

        holder.pName.setText(pu.getPersonName());
        holder.pJobProfile.setText(pu.getJobProfile());
        Picasso.get().load(pu.getImage()).placeholder(R.drawable.ic_launcher_background)
                .resize(550, 550)
                .into(holder.image);

        holder.id.setText(pu.getId());
        holder.video.setText(pu.getVideo());
        holder.audio.setText(pu.getAudio());
        holder.sourcename.setText(pu.getSourceName());
        holder.sourceimage.setText(pu.getSourceImgae());
        holder.location.setText(pu.getLocation());
        holder.language.setText(pu.getLanguage());
        holder.date.setText(pu.getDate());
        holder.long_desc.setText(pu.getLongDesc());
        holder.category.setText(pu.getCategory());

    }

    @Override
    public int getItemCount() {
        return personUtils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView pName,catcat;
        public TextView pJobProfile,id,video,audio,sourcename,sourceimage,location,language,date,long_desc,category;
        public ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);

            pName = (TextView) itemView.findViewById(R.id.title);
            pJobProfile = (TextView) itemView.findViewById(R.id.shortDesc);
            image = (ImageView) itemView.findViewById(R.id.userImg);
            id = (TextView) itemView.findViewById(R.id.id);
            video = (TextView) itemView.findViewById(R.id.video);
            audio = (TextView) itemView.findViewById(R.id.audio);
            sourcename = (TextView) itemView.findViewById(R.id.sourceName);
            sourceimage = (TextView) itemView.findViewById(R.id.sourceImage);
            location = (TextView) itemView.findViewById(R.id.location);
            language = (TextView) itemView.findViewById(R.id.language);
            date = (TextView) itemView.findViewById(R.id.date);
            long_desc = (TextView) itemView.findViewById(R.id.longDesc);
            category =(TextView) itemView.findViewById(R.id.category);


            SharedPreferences mPreferences = context.getSharedPreferences("Checker",MODE_PRIVATE);
            String daySelector = mPreferences.getString("Day1","");
            goIntent = mPreferences.getString("Day1","");

            if(daySelector.equals("yesGo")){
                Log.e("yesyesyes", "yess done");
            }else if(daySelector.equals("nono")) {
                Log.e("yesyesyes", "not done");
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PersonUtils cpu = (PersonUtils) view.getTag();
                    //Toast.makeText(view.getContext(), cpu.getPersonName()+" is "+ cpu.getJobProfile(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(view.getContext(), "Yes right thought", Toast.LENGTH_LONG).show();
///
                    SharedPreferences mPreferences;

                    if(goIntent.equals("nono")){
                        Intent intent = new Intent(view.getContext(), EditData.class);
                        intent.putExtra("title", cpu.getPersonName());
                        intent.putExtra("short", cpu.getJobProfile());
                        intent.putExtra("image", cpu.getImage());
                        intent.putExtra("id", cpu.getId());
                        intent.putExtra("video", cpu.getVideo());
                        intent.putExtra("audio",cpu.getAudio());
                        intent.putExtra("srname", cpu.getSourceName());
                        intent.putExtra("srimage", cpu.getSourceImgae());
                        intent.putExtra("location", cpu.getLocation());
                        intent.putExtra("language", cpu.getLanguage());
                        intent.putExtra("date", cpu.getDate());
                        intent.putExtra("long", cpu.getLongDesc());
                        intent.putExtra("category", cpu.getCategory());
                        view.getContext().startActivity(intent);
                    }
                    else if(goIntent.equals("yesGo")){
                        Intent intent = new Intent(view.getContext(), Accept.class);
                        intent.putExtra("title", cpu.getPersonName());
                        intent.putExtra("short", cpu.getJobProfile());
                        intent.putExtra("image", cpu.getImage());
                        intent.putExtra("id", cpu.getId());
                        intent.putExtra("video", cpu.getVideo());
                        intent.putExtra("audio",cpu.getAudio());
                        intent.putExtra("srname", cpu.getSourceName());
                        intent.putExtra("srimage", cpu.getSourceImgae());
                        intent.putExtra("location", cpu.getLocation());
                        intent.putExtra("language", cpu.getLanguage());
                        intent.putExtra("date", cpu.getDate());
                        intent.putExtra("long", cpu.getLongDesc());
                        intent.putExtra("category", cpu.getCategory());
                        view.getContext().startActivity(intent);
                    }
                }
            });

        }
    }

}
package br.com.fiap.JoseVictor_rm95663

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.alunoresponsvel_rm94532.R

class EcoTipAdapter(private var tips: List<EcoTip>) : RecyclerView.Adapter<EcoTipAdapter.TipViewHolder>(), Filterable {

    private var filteredTips: List<EcoTip> = tips

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_ecotip, parent, false)
        return TipViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TipViewHolder, position: Int) {
        val ecoTip = filteredTips[position]
        holder.bind(ecoTip)
    }

    override fun getItemCount(): Int {
        return filteredTips.size
    }

    inner class TipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tituloDica)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descricaoDica)

        fun bind(tip: EcoTip) {
            titleTextView.text = tip.title
            descriptionTextView.text = tip.description

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "Tip: ${tip.title}\n${tip.description}", Toast.LENGTH_SHORT).show()
            }

            // Intent para abrir o link de dicas sustentáveis
            itemView.setOnLongClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://brasilescola.uol.com.br/geografia/desenvolvimento-sustentavel.htm"))
                itemView.context.startActivity(browserIntent)
                true
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                filteredTips = if (constraint.isNullOrEmpty()) {
                    tips
                } else {
                    tips.filter {
                        it.title.contains(constraint, ignoreCase = true) ||
                                it.description.contains(constraint, ignoreCase = true)
                    }
                }
                filterResults.values = filteredTips
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredTips = results?.values as List<EcoTip>
                notifyDataSetChanged()
            }
        }
    }
}

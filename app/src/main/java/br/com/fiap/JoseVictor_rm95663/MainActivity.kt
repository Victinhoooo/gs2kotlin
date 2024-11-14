package br.com.fiap.JoseVictor_rm95663

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import br.com.fiap.alunoresponsvel_rm94532.R

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var tipAdapter: EcoTipAdapter
    private lateinit var dbHelper: TipsDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        dbHelper = TipsDatabaseHelper(this)

        // Verifica se o banco de dados já contém dicas iniciais
        if (dbHelper.fetchAllTips().isEmpty()) {
            insertInitialTips()
        }

        // Busca todas as dicas do banco de dados
        val tips = dbHelper.fetchAllTips()
        tipAdapter = EcoTipAdapter(tips)
        recyclerView.adapter = tipAdapter

        // Configura o SearchView para filtrar as dicas
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                tipAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tipAdapter.filter.filter(newText)
                return false
            }
        })
    }

    // Função para inserir dicas iniciais no banco de dados
    private fun insertInitialTips() {
        val initialTips = listOf(
            EcoTip("Desligue aparelhos que não estão em uso", "Aparelhos eletrônicos consomem energia mesmo em modo de espera. Desconecte quando não for usar."),
            EcoTip("Use lâmpadas LED", "Lâmpadas LED consomem até 80% menos energia do que as tradicionais."),
            EcoTip("Evite o uso excessivo de água", "A água é um recurso natural limitado. Tome banhos mais curtos e evite desperdício."),
            EcoTip("Compre produtos locais", "Produtos locais não demandam transporte a longas distâncias, reduzindo a emissão de gases poluentes.")
        )
        initialTips.forEach { dbHelper.addTip(it) }
    }
}

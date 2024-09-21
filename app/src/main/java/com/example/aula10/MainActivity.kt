package com.example.aula10

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aula10.ui.theme.Aula10Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutLista()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutLista() {

    // Variáveis que vão interagir com a interface
    var nome by remember { mutableStateOf("") }
    var fone by remember { mutableStateOf("") }
    // lista de contatos pré-cadastrados
    var contatos by remember { mutableStateOf(listOf<Contato>()) }
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(18.dp)) {

        Text(text = "Agenda Telefônica", modifier = Modifier.fillMaxWidth(),
            fontSize = 22.sp, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(10.dp))

        TextField(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
            value = nome, onValueChange = { nome = it },
            label = { Text(text = "Nome do contato")})
        
        TextField(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(70.dp),
            value = fone , onValueChange = { fone = it},
            label = { Text(text = "Telefone")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
            onClick = {

                if (nome.isNotBlank() && fone.isNotBlank()) {

                    contatos = contatos + Contato(nome, fone)
                    // limpar os campos de form
                    nome = ""
                    fone = ""

                } else {

                    Toast.makeText(context,
                        "Preencha todos os campos",
                        Toast.LENGTH_SHORT).show()

                }


            }) {
            Text(text = "Salvar Contato")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Contatos Cadastrados", modifier = Modifier.fillMaxWidth(),
            fontSize = 18.sp)

        // LazyColumn = lista dinâmica de elementos
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(contatos) {
                    contato -> ContatoItem(
                contato = contato,
                onDeleteClick = {
                    // contatos irá receber novamente todos os
                    // contatos armazenados em si, MENOS, o contato
                    // que possui o botão que clicarmos
                    contatos = contatos.filter { it != contato }

                    // Mostra Toast confirmando ação de exclusão
                    Toast.makeText(context,
                        "Contato excluído",
                        Toast.LENGTH_SHORT).show()
                } )
            }
        }

        
    }

}

@Composable
fun ContatoItem(contato: Contato, onDeleteClick: () -> Unit) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)) {

        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)){
            Text(text = "${contato.nome} (${contato.fone})",
            fontSize = 20.sp)
            
            Button(onClick = onDeleteClick) {
                Text(text = "X")
            }
                        
        }
        
    }
    
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    LayoutLista()
}
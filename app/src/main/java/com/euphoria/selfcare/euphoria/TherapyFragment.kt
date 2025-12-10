package com.euphoria.selfcare.euphoria

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepalace.chatbot.utils.BotResponse
import com.codepalace.chatbot.utils.Constants
import com.codepalace.chatbot.utils.Time
import com.euphoria.selfcare.euphoria.databinding.FragmentTherapyBinding
import kotlinx.coroutines.*

// ✅ SEKARANG SEMUA CHAT BERJALAN DI SATU FRAGMENT INI SAJA
class TherapyFragment : Fragment() {

    private var _binding: FragmentTherapyBinding? = null
    private val binding get() = _binding!!

    // list pesan & adapter (sama seperti TherapyFragment2 dulu)
    private val messagesList = mutableListOf<Message>()
    private lateinit var adapter: MessagingAdapter
    private val botList = listOf("Sahabat Euphoria Self-Care 'Nina'")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTherapyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupClickEvents()

        // Salam pembuka dari bot
        customBotMessage("Hallo! kamu sedang berbicara dengan bot yaitu ${botList[0]}, ada yang bisa dibantu?")
    }

    // ---------------------- UI / RecyclerView ----------------------

    private fun setupRecyclerView() {
        adapter = MessagingAdapter()
        binding.rvMessages.adapter = adapter
        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupClickEvents() {
        // Tombol kirim → kirim pesan, TIDAK pindah Activity
        binding.btnSend.setOnClickListener {
            sendMessage()
        }

        // Saat klik kolom teks, scroll ke paling bawah
        binding.etMessage.setOnClickListener {
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main) {
                    binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Saat fragment kebuka lagi, scroll ke bawah
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                if (::adapter.isInitialized && adapter.itemCount > 0) {
                    binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }
    }

    // ---------------------- LOGIKA CHAT ----------------------

    private fun sendMessage() {
        val message = binding.etMessage.text.toString().trim()
        if (message.isEmpty()) return

        val timeStamp = Time.timeStamp()

        // Tambah pesan user
        messagesList.add(Message(message, Constants.SEND_ID, timeStamp))
        binding.etMessage.setText("")

        adapter.insertMessage(Message(message, Constants.SEND_ID, timeStamp))
        binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

        // Balasan bot
        botResponse(message)
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            delay(1000) // delay 1 detik biar berasa "ngetik"
            withContext(Dispatchers.Main) {
                val response = BotResponse.basicResponses(message)

                messagesList.add(Message(response, Constants.RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(response, Constants.RECEIVE_ID, timeStamp))
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

                // Perintah spesial
                when (response) {
                    Constants.OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://wa.me/+6289520132211/")
                        startActivity(site)
                    }
                    Constants.OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm = message.substringAfterLast("search", "")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                }
            }
        }
    }

    private fun customBotMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, Constants.RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, Constants.RECEIVE_ID, timeStamp))
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

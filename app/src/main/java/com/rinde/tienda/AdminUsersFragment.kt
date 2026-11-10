package com.rinde.tienda

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/** Gestión de usuarios: listar, crear, editar, activar/desactivar y eliminar. */
class AdminUsersFragment : Fragment(R.layout.fragment_admin_users) {

    private lateinit var adapter: UserAdapter
    private lateinit var tvEmpty: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvEmpty = view.findViewById(R.id.tvUsersEmpty)
        val rv = view.findViewById<RecyclerView>(R.id.rvUsers)

        adapter = UserAdapter(
            onEdit = { user ->
                startActivity(
                    Intent(requireContext(), UserFormActivity::class.java)
                        .putExtra(UserFormActivity.EXTRA_ID, user.id)
                )
            },
            onToggle = { user ->
                UserRepository.update(user.copy(active = !user.active))
                refresh()
            },
            onDelete = { user -> confirmDelete(user) }
        )
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        view.findViewById<MaterialButton>(R.id.btnCreateUser).setOnClickListener {
            startActivity(Intent(requireContext(), UserFormActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        val list = UserRepository.all
        adapter.submit(list)
        tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun confirmDelete(user: User) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_user_title)
            .setMessage(getString(R.string.delete_user_msg, user.name))
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.delete) { _, _ ->
                UserRepository.delete(user.id)
                refresh()
            }
            .show()
    }
}

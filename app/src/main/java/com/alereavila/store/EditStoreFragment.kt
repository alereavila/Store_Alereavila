package com.alereavila.store

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.alereavila.store.databinding.FragmentEditStoreBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class EditStoreFragment : Fragment() {
//para usar el binding
private lateinit var mBinding: FragmentEditStoreBinding
private var mActivity : MainActivity ? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mBinding= FragmentEditStoreBinding.inflate(inflater,container, false)

        return mBinding.root
    }
    //ciclo e vida de un fragment aqui se ha creado por completo
    //ya no se corre el riesgo que alguna referencia a la vista pueda ser bula
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //se declara para oder usar la super clase de la main activity
        mActivity=activity as? MainActivity

        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.setTitle(R.string.edit_store_title_add)

        setHasOptionsMenu(true)

        mBinding.etPhotoURL.addTextChangedListener {
            Glide.with(this)
                .load(mBinding.etPhotoURL.text.toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.imgPhoto)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home ->{

                mActivity?.onBackPressed()
                true
            }
            R.id.action_save->{
                val store = StoreEntity(name=mBinding.etName.text.toString().trim(),
                    phone = mBinding.etPhone.text.toString().trim(),
                    webSite = mBinding.etWebSite.text.toString().trim(),
                    photoUrl =  mBinding.etPhotoURL.text.toString().trim())
                doAsync {
                    //SE AGREGA EL ID PARA PODER ACTUALIZAR LA TIENDA CUANDO SE CREA
                    store.id=StoreApplication.dataBase.storeDao().addStore(store)
                    uiThread {
                        mActivity?.addStore(store)
                        hideKeyboard()
                        //el toast no depende de una vista para visualizarse
                        Toast.makeText(mActivity, R.string.edit_store_messagge_save_success, Toast.LENGTH_LONG).show()
                        /*Snackbar.make(mBinding.root,
                            getString(R.string.edit_store_messagge_save_success),
                            Snackbar.LENGTH_SHORT)
                            .show()*/
                        //destruimos el fragmen
                        mActivity?.onBackPressed()
                    }
                }

                true
            }
            else -> return super.onOptionsItemSelected(item)

        }
        //return super.onOptionsItemSelected(item)
    }
//para ocultar el teclado
    private fun hideKeyboard(){
        val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(view!= null){
            imm.hideSoftInputFromWindow(requireView().windowToken,0)
        }
    }

    override fun onDestroyView() {
        hideKeyboard()
        super.onDestroyView()
    }
    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title=getString(R.string.app_name)
        mActivity?.hideFab(true)
        setHasOptionsMenu(false)
        super.onDestroy()
    }
}
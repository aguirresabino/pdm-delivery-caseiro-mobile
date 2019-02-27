package io.github.aguirresabino.deliverycaseiro.view.adapter;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Link para implementação: https://gist.github.com/nesquena/c715c9b22fb873b1d259
 *
 * Fornece o cache inteligente de fragmentos registrados dentro do ViewPager.
 * Ele faz isso substituindo o instantiateItem() e armazenando em cache todos os fragmentos criados internamente.
 * Isso resolve o problema comum de precisar acessar o item atual no ViewPager.
 *
 * @param <T>
 */
public abstract class SmartFragmentStatePagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private SparseArray<T> registeredFragments = new SparseArray<T>();

    public SmartFragmentStatePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Register the fragment when the item is instantiated
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        T fragment = (T) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    // Unregister when the item is inactive
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    // Returns the fragment for the position (if instantiated)
    public T getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}

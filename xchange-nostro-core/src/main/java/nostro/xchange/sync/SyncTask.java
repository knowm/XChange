package nostro.xchange.sync;

import java.util.concurrent.Callable;

public abstract class SyncTask<T, S extends SyncService> implements Callable<T> {
    private final S syncService;

    public SyncTask(S syncService) {
        this.syncService = syncService;
    }

    public S getSyncService() {
        return syncService;
    }
}
